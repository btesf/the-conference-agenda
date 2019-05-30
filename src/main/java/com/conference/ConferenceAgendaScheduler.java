package com.conference;

import com.conference.domain.Session;
import com.conference.domain.event.Talk;
import com.conference.domain.Track;
import com.conference.exception.ConferenceAgendaException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConferenceAgendaScheduler {

    private TalkSelector talkSelector;

    public ConferenceAgendaScheduler(){

        this.talkSelector = new TalkSelector();
    }

    public List<Track> generateSchedule(Talk[] proposedTalks){

        List<Track> conferenceTracks = new ArrayList<>();

        if(proposedTalks != null && proposedTalks.length > 0){

            talkSelector.setProposedTalks(proposedTalks);

            int trackCounter = 0;
            Track track = new Track(trackCounter++);
            conferenceTracks.add(track);

            while(proposedTalks.length > 0){

                if(track.isTrackFull()){

                    track = new Track(trackCounter++);
                    conferenceTracks.add(track);
                }

                //create a new session to hold selected talks
                Session session = new Session();
                track.addToSessions(session);

                Talk[] selectedTalks = talkSelector.selectTalksForDuration((session.getType()).DURATION_IN_MINUTES);

                if(selectedTalks == null){

                    throw new ConferenceAgendaException("Some talk times are too lengthy to fit to sessions");
                }

                session.setEvents(selectedTalks);

                proposedTalks = filterUnassignedTalks(proposedTalks, selectedTalks);
                talkSelector.setProposedTalks(proposedTalks);
            }
        }

        return conferenceTracks;
    }

    private Talk[] filterUnassignedTalks(Talk[] allTalks, Talk[] assignedTalks){

        List<Talk> assignedTalksList = Arrays.asList(assignedTalks);

        return Arrays.stream(allTalks).filter(a -> !assignedTalksList.contains(a)).toArray(Talk[]::new);
    }

    public static void main(String[] args) {

        Talk[] talks = new Talk[]{new Talk("Writing Fast Tests Using Selenium", 67),
                new Talk("Overdoing it in Java", 43),
                new Talk("AngularJS for the Masses", 30),
                new Talk("Ruby Errors from Mismatched Gem Versions", 45),
                new Talk("Common Hibernate Errors", 45),
                new Talk("Rails for Java Developers", 5),
                new Talk("Face-to-Face Communication", 69),
                new Talk("Domain-Driven Development", 45),
                new Talk("What's New With Java 11", 31),
                new Talk("A Perfect Sprint Planning", 30),
                new Talk("Pair Programming vs Noise",45),
                new Talk("Java Is Not Magic", 60),
                new Talk("Ruby on Rails: Why We Should Move On", 60),
                new Talk("Clojure Ate Scala (on my project)", 45),
                new Talk("Programming in the Boondocks of Seattle", 30),
                new Talk("Ant vs. Maven vs. Gradle Build Tool for Back-End Development",30),
                new Talk("Java Legacy App Maintenance", 60),
                new Talk("A World Without Clinical Trials", 30),
                new Talk("User Interface CSS in AngularJS Apps", 30)};

        //List<packingOptions = Parser.parse(text);

        ConferenceAgendaScheduler conferenceAgendaScheduler = new ConferenceAgendaScheduler();
        List<Track> scheduledTracks = conferenceAgendaScheduler.generateSchedule(talks);

        for(Track track : scheduledTracks){

            System.out.println(track.toString());
        }
    }
}
