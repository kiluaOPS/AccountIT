package com.kilproj.AccountIT.service.Parser;

import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.domain.Customer;
import com.kilproj.AccountIT.domain.Injury;
import com.kilproj.AccountIT.exception.DateException;
import com.kilproj.AccountIT.exception.InjuryException;
import com.kilproj.AccountIT.service.*;
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
public class ParserAppointmentsService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private CustomersService customersService;

    @Autowired
    private ClinicsService clinicsService;

    @Autowired
    private InjuriesService injuriesService;

    public ArrayList<Appointment> readAppointments(String fileName) throws InjuryException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader(fileStorageService.
                    loadFileAsResource(fileName).getFile());
            scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                String[] appointmentsInfo = line.split(",");
                Customer customer = customersService.getCustomerById(appointmentsInfo[0].replaceAll("\\s+",""));
                LocalDate date = parseDate(appointmentsInfo[1].replaceAll("\\s+",""));
                String appType = appointmentsInfo[2].replaceAll("\\s+","");
                System.out.println(customer + " " + date);
//                Clinic clinic = clinicsService.getClinicById(appointmentsInfo[3]);
                Injury injury = injuriesService.getInjuryByType(appointmentsInfo[3].replaceAll("\\s+",""));
                appointments.add(new Appointment(customer, date, appType, injury));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch ( DateException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fileReader.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return appointments;
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

