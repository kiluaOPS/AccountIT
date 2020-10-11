package com.kilproj.AccountIT.controllers;

import com.kilproj.AccountIT.domain.*;
import com.kilproj.AccountIT.exception.InjuryException;
import com.kilproj.AccountIT.scraper.WeatherScraper;
import com.kilproj.AccountIT.service.*;
import com.kilproj.AccountIT.service.Parser.ParserAppointmentsService;
import com.kilproj.AccountIT.service.Parser.ParserCustomersService;
import com.kilproj.AccountIT.service.Parser.ParserEventsService;
import com.kilproj.AccountIT.service.Parser.ParserInjuriesService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload")
@CrossOrigin("http://localhost:4200")
public class UploadController {

    @Autowired
    private ParserCustomersService parserCustomersService;
    @Autowired
    private ParserAppointmentsService parserAppointmentsService;

    @Autowired
    private ParserInjuriesService parserInjuriesService;

    @Autowired
    private ParserEventsService parserEventsService;

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CustomersService customersService;
    @Autowired
    private AppointmentsService appointmentsService;
    @Autowired
    private ClinicsService clinicsService;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private InjuriesService injuriesService;
    @Autowired
    private AdvancedCustomerService advancedCustomerService;
    @Autowired
    private WeatherScraper weatherScraper;

    /* Controller for file upload operations
     * As well as requests for downloading weather
     * data from website (http://weather.com)
     */

    @PostMapping("/customers")
    public ResponseEntity<?> uploadCustomers(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        LinkedHashMap<Customer, List<Long>> customers = parserCustomersService.readCustomers(fileStorageService.getUploadFileResponse().getFileName());
        for (Map.Entry<Customer, List<Long>> entry : customers.entrySet()) {
            customersService.save(entry.getKey());
        }
        for (Map.Entry<Customer, List<Long>> entry : customers.entrySet()) {
            Customer customer = customersService.getCustomerById(entry.getKey().getId());
            for (Long i : entry.getValue()) {
                Customer relative = customersService.getCustomerById(i);
                if (relative != null) {
                    customer.addRelative(relative);
                } else {
                    System.out.println("No customer with that ID or the customer does not have relatives in the system");
                }
            }
            customersService.update(customer);
        }

        return new ResponseEntity<>("Customers Uploaded Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/appointments")
    public ResponseEntity<?> uploadAppointments(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        List<Appointment> appointments;

        try {
            appointments = parserAppointmentsService.readAppointments(fileStorageService.getUploadFileResponse().getFileName());
            appointmentsService.saveAll(appointments);
        } catch (InjuryException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.CREATED);
    }


    @PostMapping("/events")
    public ResponseEntity<?> uploadEvents(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        List<Event> events = parserEventsService.readEvents(fileStorageService.getUploadFileResponse().getFileName());
        eventsService.saveAll(events);

        return new ResponseEntity<List<Event>>(events, HttpStatus.CREATED);
    }

    @PostMapping("/injuries")
    public ResponseEntity<?> uploadInjuries(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        List<Injury> injuries = parserInjuriesService.readInjuries(fileStorageService.getUploadFileResponse().getFileName());
        injuriesService.saveAll(injuries);

        return new ResponseEntity<List<Injury>>(injuries, HttpStatus.CREATED);
    }

    @GetMapping("/weather")
    public ResponseEntity<?> downloadWeather() {
        Thread scraper = new Thread(weatherScraper);
        scraper.start();
        return new ResponseEntity<String>("Download Meteorological Information Started", HttpStatus.OK);
    }

}

