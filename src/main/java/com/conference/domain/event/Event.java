package com.conference.domain.event;

import com.conference.Constants;

public abstract class Event {

    protected String title;
    protected int minutes;
    protected String startingTime;

    public Event(String title, int minutes){

        this.title = title;
        this.minutes = minutes;
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

        StringBuilder stringBuilder = new StringBuilder(this.startingTime);
        //append space
        stringBuilder.append(" ");
        stringBuilder.append(title);
        stringBuilder.append(" ");

        if(getMinutes() == Constants.LIGHTNING_DURATION_IN_MINS){
            //if 5 minute talk, append a text "lightning"
            stringBuilder.append("lightning");

        } else {
            //append minute and text "min". e.g. "45min"
            stringBuilder.append(getMinutes());
            stringBuilder.append("min");
        }

        return stringBuilder.toString();
    }
}
