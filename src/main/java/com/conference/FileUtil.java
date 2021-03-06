package com.conference;

import com.conference.exception.ConferenceAgendaException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileUtil {

    /**
     * Reads file content and return content as a single String
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

    public static String getFileFromResource(Class clazz) throws URISyntaxException {

        URL resource = clazz.getClassLoader().getResource("proposed_talks.txt");
        Paths.get(resource.toURI()).toFile();

        return Paths.get(resource.toURI()).toString();
    }
}
