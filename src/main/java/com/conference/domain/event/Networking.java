package com.conference.domain.event;

public class Networking extends Event{

    private static final String TITLE = "Networking Event";

    public Networking(int minutes) {
        super(TITLE, minutes);
    }
}
