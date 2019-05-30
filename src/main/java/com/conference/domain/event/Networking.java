package com.conference.domain.event;

public class Networking extends Event{

    public Networking(int minutes) {
        super("Networking Event", minutes);
    }
}
