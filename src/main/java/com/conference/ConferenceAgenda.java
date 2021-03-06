package com.conference;

import com.conference.domain.Session;
import com.conference.domain.Track;
import com.conference.domain.event.Event;
import com.conference.domain.event.Talk;
import com.conference.exception.ConferenceAgendaException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entry point to the App
 */
public class ConferenceAgenda {

    private TalkSelector talkSelector;

    ConferenceAgenda(){

        this.talkSelector = new TalkSelector();
    }

    /**
     * Takes a file path containing talk descriptions, parse the text into List<Talk> and return List<Track>
     *
     * @param filePath
     * @return
     */
    public List<Track> generateScheduleFromFile(String filePath) {

        Parser parser = new Parser();
        List<Track> tracks = null;

        try{

            String text = FileUtil.getTextFromFile(filePath);
            List<Talk> talks = parser.parse(text);

            tracks = generateSchedule(talks);

        } catch (ConferenceAgendaException conferenceAgendaException){

            System.out.println(conferenceAgendaException.getMessage());
        }

        return tracks;
    }

    /**
     *  Takes a List<Talk>, organize them into track and sessions and return List<Track>
     *
     *  It uses, TaskSelector -> selectTalksForDuration(...) method to decide which talks go to which session
     */
    private List<Track> generateSchedule(List<Talk> proposedTalks){

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

    /**
     *  Runner
     *
     *  If an absolute file name is provided as an argument, it tries to read that file
     *  Otherwise, it reads the content of a file named "proposed_talks.txt" from the "resources" directory
     *
     * @param args
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws URISyntaxException {

        String fileName = (args.length > 0) ? args[0] : FileUtil.getFileFromResource(ConferenceAgenda.class);

        ConferenceAgenda conferenceAgenda = new ConferenceAgenda();
        List<Track> scheduledTracks = conferenceAgenda.generateScheduleFromFile(fileName);

        if(scheduledTracks != null)
            for(Track track : scheduledTracks){
                System.out.println(track.toString());
            }
    }
}
