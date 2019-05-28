package com.conference.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Talk {

    private String title;
    private int minutes;
    //change string to lowercase, remove spaces, and combine it with minutes to get a "Smushed" name
    private final String smushed;

    public Talk(String title, int minutes){

        this.title = title;
        this.minutes = minutes;
        this.smushed = title.toLowerCase().replaceAll(" ", "") + minutes;
    }

    public String getTitle() {
        return title;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Talk)){

            return false;
        }

        Talk secondTalk = (Talk) obj;

        return secondTalk.smushed.equals(this.smushed);
    }

    @Override
    public int hashCode() {
        return smushed.hashCode();
    }
}
