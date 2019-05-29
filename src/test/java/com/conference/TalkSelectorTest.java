package com.conference;

import com.conference.domain.Talk;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TalkSelectorTest {

    @Test
    public void testSelectTalksForDuration(){

        Talk[] talks = {new Talk("a", 50), new Talk("b", 30), new Talk("c", 20), new Talk("d", 40),
                new Talk("e", 10), new Talk("f", 70), new Talk("g", 60)};


        TalkSelector talkSelector = new TalkSelector(talks);

        //10 minutes
        Talk[] selectedTalks = talkSelector.selectTalksForDuration(10);
        int totalTalksLength = Arrays.stream(selectedTalks).mapToInt(Talk::getMinutes).sum();
        assertEquals(10, totalTalksLength);

        //137 minutes
        selectedTalks = talkSelector.selectTalksForDuration(137);
        totalTalksLength = Arrays.stream(selectedTalks).mapToInt(Talk::getMinutes).sum();
        assertEquals(totalTalksLength, 130);

        //300
        selectedTalks = talkSelector.selectTalksForDuration(300);
        totalTalksLength = Arrays.stream(selectedTalks).mapToInt(Talk::getMinutes).sum();
        assertEquals(totalTalksLength, 280);
    }
}
