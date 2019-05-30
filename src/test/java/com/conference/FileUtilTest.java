package com.conference;

import com.conference.exception.ConferenceAgendaException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import static org.junit.Assert.*;

public class FileUtilTest {

    String fileName;

    @Before
    public void before() {

        File resourcesDirectory = new File("src/test/resources");
        fileName = resourcesDirectory.getAbsolutePath() + "/dummy_file.txt";
    }

    @Test(expected = ConferenceAgendaException.class)
    public void testGetTextFromFile_NonExistingFile(){

        FileUtil.getTextFromFile("non_existent_files.txt");
    }

    @Test
    public void testGetTextFromFile_ExistingFile(){

        try {

            String text = FileUtil.getTextFromFile(fileName);
            assertEquals("dummy\ntext", text);

        } catch (ConferenceAgendaException e) {
            //shouldn't come here - means File not found
            fail();
        }
    }
}
