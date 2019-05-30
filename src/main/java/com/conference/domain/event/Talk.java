package com.conference.domain.event;

import com.conference.Constants;

public class Talk extends Event implements Comparable<Talk> {

    private int id; //hash code of "smushed name"

    public Talk(String title, int minutes){

        super(title, minutes);
        this.id = getSmushedName(title, minutes).hashCode();
    }
    /*
     change string to lowercase, remove spaces, and combine it with minutes to get a "Smushed" name
     */
    private String getSmushedName(String title, int minutes){

        return title.toLowerCase().replaceAll(" ", "") + minutes;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Talk)){

            return false;
        }

        Talk secondTalk = (Talk) obj;

        return secondTalk.id == this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public int compareTo(Talk secondTalk) {

       return this.minutes - secondTalk.minutes;
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
