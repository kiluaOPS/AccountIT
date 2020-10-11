package com.kilproj.AccountIT.service.statistics;

import com.kilproj.AccountIT.domain.Customer;
import com.kilproj.AccountIT.domain.Event;
import com.kilproj.AccountIT.geocoding.Geocoding;
import com.kilproj.AccountIT.geocoding.GeocodingException;
import com.kilproj.AccountIT.geocoding.GeocodingImpl;
import com.kilproj.AccountIT.service.AppointmentsService;
import com.kilproj.AccountIT.service.ClinicsService;
import com.kilproj.AccountIT.service.CustomersService;
import com.kilproj.AccountIT.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EventStatisticsService {

    @Autowired
    private EventsService eventsService;

    @Autowired
    private CustomersService customersService;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private ClinicsService clinicsService;

    private Geocoding geocoding = new GeocodingImpl();

    /**
     * Get all customers that joined after the event (in a TIME_FROM_EVENT period)
     * that are living in a radius of DISTANCE_FROM_EVENT kilometers.
     *
     * @param id of the event
     * @return list of customers related to the event
     */
    public HashMap<Event, List<Customer>> getCustomersFromEvent(Long id, int distance_from_event) {
        Event event = eventsService.getEventById(id);
        LocalDate eventDate = event.getDate();
        LocalDate endDate = eventDate.plusMonths(clinicsService.getClinic().getTimeFromEvent());
        List<Customer> customersIn = customersService.getCustomerJoinedAfterDate(eventDate, endDate);
        List<Customer> customersOut = new ArrayList<>();
        for (Customer customer : customersIn) {
            double distance = 0;
            if (customer.getRefType().equals("event")) {
                try {
                    distance = geocoding.getDistance(event.getLatitude(), event.getLongitude(), customer.getLatitude(), customer.getLongitude(), "K");
                    if (distance < distance_from_event && customer.getRefType().equals("event")) {
                        customersOut.add(customer);
                    }
                } catch (GeocodingException e) {
                    e.printStackTrace();
                }
            }
        }
        HashMap<Event, List<Customer>> map = new HashMap<>();
        map.put(event, customersOut);
        return map;
    }
}

