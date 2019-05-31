package com.conference.domain.event;

/**
 *  Event is anything that happens in a day(Track).
 *  It can be a Talk, a Networking Event or even a Lunch
 */
public abstract class Event {

    protected String title;
    protected int minutes;
    protected String startingTime;

    public Event(String title, int minutes){

        this.title = title;
        this.minutes = minutes;
    }

    public Event(String title){

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    @Override
    public String toString() {

        return this.startingTime + " " + title;
    }
}
