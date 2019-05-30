package com.conference;

import com.conference.domain.event.Talk;
import com.conference.exception.ConferenceAgendaException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public List<Talk> parse(String text) {

        if(text == null || text.trim().equals("")){

            throw new ConferenceAgendaException("File content is empty.");
        }

        return getTalks(text);
    }
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

            return Constants.LIGHTNING_DURATION_IN_MINS;

        } else {
            //remove the text "min"
            duration = duration.replace("min", "");
            return Integer.valueOf(duration);
        }
    }

    private String[] getStringLines(String text) {

        if (text == null || text.trim().equals("")) {

            throw new ConferenceAgendaException("Empty file content.");
        }

        return text.split("[\\r?\\n]+");//ignores multiple new lines
    }


    private List<Talk> getTalks(String text)  {

        if(text == null || text.trim().equals("")){

            throw new ConferenceAgendaException("File content is empty.");
        }

        List<Talk> proposedTalks = new ArrayList<>();
        String[] lines = getStringLines(text);

        if(lines.length < 2){

            throw new ConferenceAgendaException("The file should contain at least two lines; 1st line: No of talks, Next lines: Talk titles and time in minutes");
        }

        //first line will be the number of proposed talks listed in the file
        String noOfTalksString = lines[0];
        int noOfTalks;

        try {

            noOfTalks = Integer.valueOf(noOfTalksString);

        } catch (NumberFormatException e){

            throw new ConferenceAgendaException("The first line in the file should be a number - number of talks in the file");
        }

        for(int i = 1; i <= noOfTalks; i++){

            String line = lines[i];
            String cleanedUpString = cleanupString(line);

            proposedTalks.add(getTalk(cleanedUpString));
        }

        return proposedTalks;
    }

    /**
     * trimming and any further cleanup and sanitizing work goes here
     *
     * @param talkDescription
     * @return
     */
    private String cleanupString(String talkDescription) {

        return talkDescription.trim();
    }


}
