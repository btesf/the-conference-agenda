package com.conference;

import com.conference.domain.event.Talk;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

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
}
