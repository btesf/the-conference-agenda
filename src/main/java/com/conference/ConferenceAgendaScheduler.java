package com.conference;

import com.conference.domain.Session;
import com.conference.domain.Track;
import com.conference.domain.event.Event;
import com.conference.domain.event.Talk;
import com.conference.exception.ConferenceAgendaException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConferenceAgendaScheduler {

    private TalkSelector talkSelector;

    public ConferenceAgendaScheduler(){

        this.talkSelector = new TalkSelector();
    }

    public List<Track> generateSchedule(List<Talk> proposedTalks){

        List<Track> conferenceTracks = new ArrayList<>();

        if(proposedTalks != null && proposedTalks.size() > 0){

            talkSelector.setProposedTalks(proposedTalks);

            int trackCounter = 1;
            Track track = new Track(trackCounter++);
            conferenceTracks.add(track);

            while(proposedTalks.size() > 0){

                if(track.isTrackFull()){

                    track = new Track(trackCounter++);
                    conferenceTracks.add(track);
                }

                //create a new session to hold selected talks
                Session session = new Session();
                track.addToSessions(session);
                //session.setEvents expects list of Event - a parent of Talk. So we generify the list
                List<? extends Event> selectedTalks = talkSelector.selectTalksForDuration((session.getType()).DURATION_IN_MINUTES);

                if(selectedTalks == null){

                    throw new ConferenceAgendaException("Some talk times are too lengthy to fit to sessions");
                }

                session.setEvents((List<Event>)selectedTalks);

                proposedTalks = filterUnassignedTalks(proposedTalks, (List<Talk>)selectedTalks);
                talkSelector.setProposedTalks(proposedTalks);
            }
        }

        return conferenceTracks;
    }

    private List<Talk> filterUnassignedTalks(List<Talk> allTalks, List<Talk> assignedTalks){

        return allTalks.stream().filter(a -> !assignedTalks.contains(a)).collect(Collectors.toList());
    }

    public List<Track> generateSchedule(String filePath) {

        Parser parser = new Parser();
        String text = FileUtil.getTextFromFile(filePath);

        List<Talk> talks = parser.parse(text);

        return generateSchedule(talks);
    }

    public static void main(String[] args) throws URISyntaxException {

        ConferenceAgendaScheduler conferenceAgendaScheduler = new ConferenceAgendaScheduler();
        URL resource = ConferenceAgendaScheduler.class.getClassLoader().getResource("proposed_talks.txt");
        Paths.get(resource.toURI()).toFile();
        String fileName = Paths.get(resource.toURI()).toString();
        List<Track> scheduledTracks = conferenceAgendaScheduler.generateSchedule(fileName);

        for(Track track : scheduledTracks){
            System.out.println(track.toString());
        }
    }
}
