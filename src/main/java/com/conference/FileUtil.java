package com.conference;

import com.conference.exception.ConferenceAgendaException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class FileUtil {

    /**
     * Reads file content and return content as String
     *
     * @return
     */
    public static String getTextFromFile(String path) {

        File file = new File(path);
        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(file));

        } catch (FileNotFoundException e) {

            throw new ConferenceAgendaException("File '" + path + "' not found.");
        }

        //Read lines from file and join them by system specific line separator
        String lines = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        return lines;
    }
}
