package com.conference.domain;

public class Talk implements Comparable<Talk> {

    private String title;
    private int minutes;
    private int id; //hash code of "smushed name"

    public Talk(String title, int minutes){

        this.title = title;
        this.minutes = minutes;
        this.id = getSmushedName(title, minutes).hashCode();
    }

    public String getTitle() {
        return title;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getId() {
        return this.id;
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
