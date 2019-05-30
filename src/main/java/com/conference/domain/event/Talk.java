package com.conference.domain.event;

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
}
