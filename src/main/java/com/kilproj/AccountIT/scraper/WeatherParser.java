package com.kilproj.AccountIT.scraper;

import com.kilproj.AccountIT.domain.WeatherDay;
import com.kilproj.AccountIT.exception.DateException;
import com.kilproj.AccountIT.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WeatherParser {

    @Autowired
    private WeatherService weatherService;

    private String folderName = "weather-data";

    public WeatherParser() {
    }

    public void read() {
        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        });

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
            } else if (listOfFiles[i].isDirectory()) {
            }
        }
        for (File file : listOfFiles) {
            try {
                List<WeatherDay> weatherDays = new ArrayList<>();
                FileReader fr = new FileReader(file);
                Scanner scanner = new Scanner(fr);
                scanner.nextLine();
                String[] monthYear = getYearAndMonth(file.getName());
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] split = line.split(",");
                    int month = Integer.parseInt(monthYear[1]);
                    int year = Integer.parseInt(monthYear[0]);
                    int actualDays = invalidDataCleaner(month - 1, year);
                    if (Integer.parseInt(split[1]) <= actualDays) {
                        LocalDate date = parseDate(split[1] + "-" + monthYear[1] + "-" + monthYear[0]);
                        int lowTemp = extractInt(split[2]);
                        int highTemp = extractInt(split[3]);
                        WeatherDay wd = new WeatherDay(date, highTemp, lowTemp);
                        weatherDays.add(wd);
                    }
                }
                List<WeatherDay> wd = removeInvalidWeatherDays(weatherDays);
                weatherService.saveAll(wd);

            } catch (FileNotFoundException e) {
                System.out.println("FILE WAS NOT FOUND!");
                e.printStackTrace();
            } catch (DateException e) {
                System.out.println("Weather Date was not Found");
            }
        }
    }


    private static int invalidDataCleaner(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private String[] getYearAndMonth(String dateString) {
        String[] split = dateString.split("-");
        return split;
    }

    private LocalDate parseDate(String dateString) throws DateException {
        SimpleDateFormat[] formats = new SimpleDateFormat[]
                {new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("d-MMM-yy"),
                        new SimpleDateFormat("d-MMM-yyyy"), new SimpleDateFormat("dd-MMM-yyyy"),
                        new SimpleDateFormat("dd/MM/yyyy")};

        Date parsedDate = null;

        for (int i = 0; i < formats.length; i++) {
            try {
                parsedDate = formats[i].parse(dateString);
                return parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                continue;
            }
        }
        throw new DateException("Unknown date format: '" + dateString + "'");
    }

    private int extractInt(String str) {
        Matcher matcher = Pattern.compile("\\d+").matcher(str);

        if (!matcher.find())
            return 0;

        return Integer.parseInt(matcher.group());
    }

    private List<WeatherDay> removeInvalidWeatherDays(List<WeatherDay> weatherDays) {
        List<WeatherDay> toRemove = new ArrayList<>();

        int max = -1;
        boolean headPassed = false;
        for (WeatherDay wd : weatherDays) {
            int day = wd.getDate().getDayOfMonth();
            if (day < max) {
                toRemove.add(wd);
            }
            if (!headPassed && day==1) {
                headPassed = true;
            } else if (headPassed) {
                if (day > max) {
                    max = day;
                }
                continue;
            } else {
                toRemove.add(wd);
            }
        }
        weatherDays.removeAll(toRemove);
        return weatherDays;
    }
}
