package com.kilproj.AccountIT.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class WeatherScraper implements Runnable {

    @Autowired
    private WeatherParser weatherParser;

    @Override
    public void run() {

        String folder = "weather-data";
        deleteFiles(folder);
        try {
            Runtime runtime = Runtime.getRuntime();

        /*
        Add Jython to interpret the script
        https://python-3-patterns-idioms-test.readthedocs.io/en/latest/Jython.html
         */
            Process process = runtime.exec((new String[]{"C://Anaconda3//python.exe",
                    "python scraper//scraper.py", folder}));
            BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        weatherParser.read();
    }

    private void deleteFiles(String folder) {

        File index = new File(folder);
        if (index.length() == 0) {
            return;
        }
        String[] entries = index.list();
        for (String s : entries) {
            File currentFile = new File(index.getPath(), s);
            currentFile.delete();
        }
        index.delete();
    }
}
