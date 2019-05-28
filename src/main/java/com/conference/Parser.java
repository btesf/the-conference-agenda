package com.conference;

import com.conference.domain.Talk;
import com.conference.exception.ConferenceAgendaException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    /**
     * create a Talk Instance from a string containing Talk description
     *
     * e.g talkDescription = "Writing Fast Tests Using Selenium 60min"
     *
     * Talk will be new Talk(title: "Writing Fast Tests Using Selenium", minutes: 60)
     *
     * Throws an exception if talkDescription doesn't confirm to the ff. constraint:
     *
     *  -  No talk title has numbers in it
     *  -  All talk lengths are either in numbers that represent minutes or the text "lightning" that represents 5 minutes
     *
     * @param talkDescription
     * @return
     */
    public Talk getTalk(String talkDescription) {

        final String patternString = "^(.+)(\\s+)([0-9]+min|lightning)$";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(talkDescription);

        if(!matcher.find()){

            throw new ConferenceAgendaException("Talk Description is not formatted correctly.");
        }

        String title = matcher.group(1);
        String duration = matcher.group(3);

        return new Talk(title, getDurationFromString(duration));
    }

    /**
     * duration can be a number or a token "lightning" to represent 5 mins
     *
     * @param duration
     * @return
     */
    private int getDurationFromString(String duration){

        if(duration.toLowerCase().equals("lightning")){

            return 5;

        } else {
            //remove the text "min"
            duration = duration.replace("min", "");
            return Integer.valueOf(duration);
        }
    }
}
