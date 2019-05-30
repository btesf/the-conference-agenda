package com.conference;

import com.conference.domain.Session;
import com.conference.domain.Track;
import com.conference.domain.event.Event;
import com.conference.domain.event.Talk;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConferenceAgendaSchedulerTest {

    @Test
    public void testGenerateSchedule() throws URISyntaxException {

        List<Track> trackList = new ArrayList();
        Track track = new Track(1);
        Session session = new Session();
        List<Event> talks =  new ArrayList<>();

        track.addToSessions(session);
        Collections.addAll(talks, new Talk("Writing Fast Tests Using Selenium", 60), new Talk("Overdoing it in Java", 5));
        session.setEvents(talks);
        trackList.add(track);

        URL resource = ConferenceAgendaSchedulerTest.class.getClassLoader().getResource("proposed_talks.txt");
        Paths.get(resource.toURI()).toFile();
        String fileName = Paths.get(resource.toURI()).toString();
        ConferenceAgendaScheduler conferenceAgendaScheduler = new ConferenceAgendaScheduler();

        List<Track> actualResult = conferenceAgendaScheduler.generateSchedule(fileName);
        assertEquals(actualResult.get(0).toString(), trackList.get(0).toString());
    }
}
