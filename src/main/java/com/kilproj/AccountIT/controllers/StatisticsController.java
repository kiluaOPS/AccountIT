package com.kilproj.AccountIT.controllers;

import com.kilproj.AccountIT.domain.AdvancedCustomer;
import com.kilproj.AccountIT.domain.Injury;
import com.kilproj.AccountIT.exception.CustomerException;
import com.kilproj.AccountIT.service.*;
import com.kilproj.AccountIT.service.statistics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/statistics")

@CrossOrigin("http://localhost:4200")
public class StatisticsController {
    /* Controller for all statistics calls.
     */

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private EventStatisticsService eventStatisticsService;

    @Autowired
    private CustomersStatisticsService customersStatisticsService;

    @Autowired
    private AppointmentsStatisticsService appointmentsStatisticsService;

    @Autowired
    private AdvancedCustomerService advancedCustomerService;

    @Autowired
    private InjuriesStatisticsService injuriesStatisticsService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherStatisticsService weatherStatisticsService;

    @Autowired
    private ClinicsService clinicsService;

    /*
     * Customer Statistics
     */


    /* Return advanced customer by id
     */
    @GetMapping("/advanced-customer/{id}")
    public ResponseEntity<?> getAdvancedCustomerById(@PathVariable("id") String id) {
        AdvancedCustomer customer = null;
        try {
            customer = injuriesStatisticsService.getAdvancedCustomerById(id);
            customer.applyDateInjuryFilter(clinicsService.getClinic().getMaxNumberOfDays());
            System.out.println(customer.getAppointmentsSets().size());
        } catch (CustomerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /* Return all advanced customers in database
     */
    @GetMapping("/advanced-customers/")
    public List<AdvancedCustomer> getAdvancedCustomer() {
        List<AdvancedCustomer> customers = advancedCustomerService.getAdvancedCustomers();
        return customers;
    }

    /* Return age distribution of customers
     */
    @GetMapping("/customers/age-distribution")
    public ResponseEntity<?> getCustomersAgeDistribution() {
        return new ResponseEntity<>(customersStatisticsService.getCustomersAgeDistribution(), HttpStatus.OK);
    }

    /* For the customer referred by the ID return the injuries of all relatives
     */
    @GetMapping("/customer/relatives-injuries/{id}")
    public ResponseEntity<?> getCustomerRelativesInjuries(@PathVariable("id") String customerId) {
        HashMap<Injury, Integer> injuriesReport = customersStatisticsService.getRelativesInjuriesOrder(customerId);
        return new ResponseEntity<>(injuriesReport, HttpStatus.OK);
    }


    /* Each mapping is linked to a service method. These are
     * discussed more in detail in each service.
     *
     *
     * Marketing Statistics
     */


    @GetMapping("/average-treatment-length")
    public ResponseEntity<?> getAverageTreatmentLength() {
        return new ResponseEntity<>(statisticsService.getAverageTreatmentSet(), HttpStatus.OK);
    }

    @GetMapping("/dissatisfied-customers")
    public ResponseEntity<?> getDissatisfiedCustomers() {
        return new ResponseEntity<>(customersStatisticsService.getDiscontentCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customers/ref-influence")
    public ResponseEntity<?> getReferralInfluence() {
        return new ResponseEntity<>(customersStatisticsService.getReferralInfluence(), HttpStatus.OK);
    }

    @GetMapping("/appointments/filling-rate-monthly/{year}")
    public ResponseEntity<?> getMonthlyFillingRates(@PathVariable("year") int year) {
        return new ResponseEntity<>(appointmentsStatisticsService.getMonthlyFillingRates(year), HttpStatus.OK);
    }

    @GetMapping("/appointments/filling-rate-weekly/{year}")
    public ResponseEntity<?> getWeeklyFillingRates(@PathVariable("year") int year) {
        return new ResponseEntity<>(appointmentsStatisticsService.getWeeklyFillingRate(year), HttpStatus.OK);
    }

    /*
     * Injuries Statistics
     */

    @GetMapping("/injuries-recovery-time")
    public ResponseEntity<?> getInjuriesRecoveryStatistics() {
        return new ResponseEntity<>(injuriesStatisticsService.getInjuryMeanRecoveryTime(), HttpStatus.OK);
    }

    @GetMapping("/injuries-recovery-time/age")
    public ResponseEntity<?> getAgeInjuryRelation() {
        return new ResponseEntity<>(injuriesStatisticsService.getInjuryAgeRecoveryInfluence(), HttpStatus.OK);
    }

    /*
     * Events Statistics
     */

    @GetMapping("/event")
    public ResponseEntity<?> getEventInfluence(@RequestParam(name = "id") long eventId, @RequestParam(name = "distance") int distance) {
        return new ResponseEntity<>(eventStatisticsService.getCustomersFromEvent(eventId, distance), HttpStatus.OK);
    }

    @GetMapping("/weather/{year}")
    public ResponseEntity<?> getWeatherFillingRelation(@PathVariable("year") int year) {
        return new ResponseEntity<>(weatherStatisticsService.getYearFillingRelation(year), HttpStatus.OK);
    }

    @GetMapping("/weather/super-pose/{year}")
    public ResponseEntity<?> test1(@PathVariable("year") int year) {
        return new ResponseEntity<>(weatherStatisticsService.getCurvesKFactor(year), HttpStatus.OK);
    }
}
