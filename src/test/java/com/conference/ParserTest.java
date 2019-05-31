package com.conference;

import com.conference.domain.event.Talk;
import com.conference.exception.ConferenceAgendaException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ParserTest {

    @Parameterized.Parameter(0)
    public String _talkDescription;
    @Parameterized.Parameter(1)
    public Talk _talk;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Object[][] data = new Object[][] {
                {
                    "Writing Fast Tests Using Selenium 60min",
                     new Talk("Writing Fast Tests Using Selenium", 60)
                },
                {
                    "Overdoing it in Java 45min",
                     new Talk("Overdoing it in Java", 45)
                },
                {
                    "Rails for Java Developers lightning",
                     new Talk("Rails for Java Developers", 5)
                },
                {
                    "A World Without Clinical Trials lightning",
                    new Talk("A World Without Clinical Trials", 5)
                }
        };

        return Arrays.asList(data);
    }

    @Test
    public void testGetTalk(){

        Parser parser = new Parser();

        Talk talk = parser.getTalk(_talkDescription);

        assertEquals(talk, _talk);
    }

    @Test(expected = ConferenceAgendaException.class)
    public void testParse_NoOfLinesExceed20(){

        Parser parser = new Parser();
        String text = "22 \n line_1 \n line_2 \n line_3 \n line_4 \n line_5 \n line_6 \n line_7 \n line_8 \n,  line_9 \n,  line_10 \n" +
                "\n line_11 \n line_12 \n line_13 \n line_14 \n line_15 \n line_16 \n line_17 \n line_18 \n,  line_19 \n,  line_20 \n";

        parser.parse(text);
    }

    @Test
    public void testParse(){

        Parser parser = new Parser();
        String text = "2 \n Talk one 12min \n Talk two lightning \n";
        List<Talk> expectedTalks = new ArrayList<>();
        Collections.addAll(expectedTalks, new Talk("Talk one", 12), new Talk("Talk two", 5));

        List<Talk> actualResult = parser.parse(text);

        assertThat(actualResult, is(expectedTalks));
    }
}
