package com.conference;

import com.conference.domain.event.Talk;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TalkSelectorTest {

    private List<Talk> talks;
    private TalkSelector talkSelector;

    @Before
    public void before(){

        talks= new ArrayList<>();

        Collections.addAll(talks, new Talk("a", 50), new Talk("b", 30),
                new Talk("c", 20), new Talk("d", 40),
                new Talk("e", 10), new Talk("f", 70),
                new Talk("g", 60));

        talkSelector = new TalkSelector();
        talkSelector.setProposedTalks(talks);
    }


    @Test
    public void testSelectTalksForDuration(){

        //10 minutes
        List<Talk> selectedTalks = talkSelector.selectTalksForDuration(10);
        int totalTalksLength = selectedTalks.stream().mapToInt(Talk::getMinutes).sum();
        assertEquals(10, totalTalksLength);

        //137 minutes
        selectedTalks = talkSelector.selectTalksForDuration(137);
        totalTalksLength = selectedTalks.stream().mapToInt(Talk::getMinutes).sum();
        assertEquals(totalTalksLength, 130);

        //300
        selectedTalks = talkSelector.selectTalksForDuration(300);
        totalTalksLength = selectedTalks.stream().mapToInt(Talk::getMinutes).sum();
        assertEquals(totalTalksLength, 280);
    }

    @Test
    public void testCollectMarkedTalks(){

        int[] visitedIndeces = new int[]{0, 0, 1, 1, 0, 0, 0};
        List<Talk> selectedTalks = talkSelector.collectMarkedTalks(visitedIndeces.length - 1, visitedIndeces);
        List<Talk> expectedTalks = new ArrayList<>();

        Collections.addAll(expectedTalks, new Talk("c", 20), new Talk("d", 40));

        assertThat(selectedTalks, is(expectedTalks));
    }
}
