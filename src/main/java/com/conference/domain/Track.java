package com.conference.domain;


import com.conference.exception.ConferenceAgendaException;

import java.util.ArrayList;
import java.util.List;

/**
 * A "Track" represents a day in which a maximum of two sessions of talks run
 */
public class Track {

    private final int trackNumber;
    private List<Session> sessions;
    private final int ALLOWED_SESSION_COUNT = 2;

    public Track(int trackNumber){

        sessions = new ArrayList<>();
        this.trackNumber = trackNumber;
    }
    /**
     * A track will have ALLOWED_SESSION_COUNT number of sessions.
     * The first one will be a Morning session, and the second, an Afternoon session
     *
     * when we add the first session for the track, then we set it to a MORNING session. But if there
     * is one already, the added session will be of an AFTERNOON type
     *
     * @param session
     */
    public void addToSessions(Session session){

        if(isTrackFull()){

            throw new ConferenceAgendaException("Track is full. Cannot contain more than " + ALLOWED_SESSION_COUNT + " sessions per track");
        }

        SessionType sessionType = sessions.size() == 0 ? SessionType.MORNING : SessionType.AFTERNOON;

        session.setType(sessionType);
        sessions.add(session);
    }

    public boolean isTrackFull(){

        return sessions.size() == ALLOWED_SESSION_COUNT;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder("Track ");

        stringBuilder.append(trackNumber);
        stringBuilder.append(":");
        stringBuilder.append(System.getProperty("line.separator"));

        for(Session session : sessions){

            stringBuilder.append(session.toString());
            stringBuilder.append(System.getProperty("line.separator"));
        }

        return stringBuilder.toString();
    }
}
