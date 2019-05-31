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

public class ConferenceAgendaTest {

    /**
     * The content of the file "proposed_talks.txt" should be arranged into two Sessions. First session has two talks,
     * and the afternoon session will contain a longer more than 3 hour talk
     * @throws URISyntaxException
     */
    @Test
    public void testGenerateSchedule() throws URISyntaxException {

        List<Track> trackList = new ArrayList();
        Track track = new Track(1);
        Session morningSession = new Session();
        Session afternoonSession = new Session();
        List<Event> morningTalks =  new ArrayList<>();
        List<Event> afternoonTalks =  new ArrayList<>();

        track.addToSessions(morningSession);
        track.addToSessions(afternoonSession);

        Collections.addAll(morningTalks, new Talk("Writing Fast Tests Using Selenium", 60),
                new Talk("Overdoing it in Java", 5));
        morningSession.setEvents(morningTalks);

        Collections.addAll(afternoonTalks, new Talk("Longer than three hours talk", 190));
        afternoonSession.setEvents(afternoonTalks);

        trackList.add(track);
        String fileName =  FileUtil.getFileFromResource(ConferenceAgendaTest.class);
        ConferenceAgenda conferenceAgenda = new ConferenceAgenda();

        List<Track> actualResult = conferenceAgenda.generateScheduleFromFile(fileName);
        assertEquals(actualResult.get(0).toString(), trackList.get(0).toString());
    }
}
