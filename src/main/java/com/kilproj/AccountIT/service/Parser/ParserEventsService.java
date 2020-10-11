package com.kilproj.AccountIT.service.Parser;

import com.kilproj.AccountIT.domain.Event;
import com.kilproj.AccountIT.exception.DateException;
import com.kilproj.AccountIT.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

@Service
public class ParserEventsService {

    @Autowired
    private FileStorageService fileStorageService;

    public ArrayList<Event> readEvents(String fileName) {
        ArrayList<Event> events = new ArrayList<>();
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader(fileStorageService.
                    loadFileAsResource(fileName).getFile());
            scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                //logic for injuries
                String eventLine = scanner.nextLine();
                String[] eventSplit = eventLine.split(",");
                String name = eventSplit[0];
                String address = eventSplit[1].replaceAll("\\s+", "");
                LocalDate date = parseDate(eventSplit[2].replaceAll("\\s+", ""));
                events.add(new Event(name, date, address));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fileReader.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return events;
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

}
