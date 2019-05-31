package com.conference.domain;

import com.conference.domain.event.Event;
import com.conference.domain.event.Lunch;
import com.conference.domain.event.Networking;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A "Session" represents a morning or afternoon part of a day in which "Event"s happen. E.g Talk presentation, or Networking Event
 */
public class Session {

    private List<Event> events; //use List instead of Array here because it might resize when adding more events dynamically such as 'Networkign Event'
    private SessionType type;

    public SessionType getType() {

        return type;
    }

    public void setType(SessionType sessionType){

        this.type = sessionType;
    }

    public void setEvents(List<Event> events){

        this.events = events;

        if(events.size() > 0){
            //if the total time of all events (talks at this point) is less than the allocated time for the session, add a networking event
            int totalTalkTimeInMins = events.stream().mapToInt(Event::getMinutes).sum();

            if(totalTalkTimeInMins < type.DURATION_IN_MINUTES){

                Networking networkingEvent = new Networking(type.DURATION_IN_MINUTES - totalTalkTimeInMins);

                this.events.add(networkingEvent);
            }

            //for Morning sessions, add Lunch events
            if(type.equals(SessionType.MORNING)){

                Lunch lunchEvent = new Lunch();

                this.events.add(lunchEvent);
            }

            setTalkBeginTimes();
        }
    }

    private void setTalkBeginTimes() {

        LocalTime time = LocalTime.of(type.SESSION_BEGIN_TIME, 0);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        for(Event event : events){

            event.setStartingTime(time.format(timeFormatter));
            time = time.plusMinutes(event.getMinutes());
        }
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        int noOfTalks = events.size();

        for(int i = 0; i < noOfTalks; i++){

            stringBuilder.append(events.get(i).toString());

            if(i < noOfTalks - 1) stringBuilder.append(System.getProperty("line.separator"));
        }

        return stringBuilder.toString();
    }
}
