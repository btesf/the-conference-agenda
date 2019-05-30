package com.conference;

import com.conference.domain.event.Talk;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TalkSelectorTest {

    private Talk[] talks;
    private TalkSelector talkSelector;

    @Before
    public void before(){

        List<Talk> talkList = new ArrayList<>();

        Collections.addAll(talkList, new Talk("a", 50), new Talk("b", 30),
                new Talk("c", 20), new Talk("d", 40),
                new Talk("e", 10), new Talk("f", 70),
                new Talk("g", 60));

        talks = talkList.toArray(new Talk[talkList.size()]);
        talkSelector = new TalkSelector();
        talkSelector.setProposedTalks(talks);
    }


    @Test
    public void testSelectTalksForDuration(){

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

    @Test
    public void testCollectMarkedTalks(){

        int[] visitedIndeces = new int[]{0, 0, 1, 1, 0, 0, 0};
        Talk[] selectedTalks = talkSelector.collectMarkedTalks(visitedIndeces.length - 1, visitedIndeces);

        assertArrayEquals(selectedTalks, new Talk[]{new Talk("c", 20), new Talk("d", 40)});
    }
}
