package com.conference.domain;

/**
 * A "Session" represents a morning or afternoon part of a day in which talks will be presented
 */
public class Session {

    private Talk[] talks;
    private SessionType type;

    Session(Talk[] talks){

        this.talks = talks;
    }

    public SessionType getType() {
        return type;
    }

    public void setType(SessionType sessionType){

        this.type = sessionType;
    }

    public Talk[] getTalks() {
        return talks;
    }
}
