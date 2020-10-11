package com.kilproj.AccountIT.service.statistics;

import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.domain.Customer;
import com.kilproj.AccountIT.domain.AdvancedCustomer;
import com.kilproj.AccountIT.domain.Injury;
import com.kilproj.AccountIT.service.AdvancedCustomerService;
import com.kilproj.AccountIT.service.AppointmentsService;
import com.kilproj.AccountIT.service.ClinicsService;
import com.kilproj.AccountIT.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CustomersStatisticsService {

    @Autowired
    private CustomersService customersService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private AdvancedCustomerService advancedCustomerService;

    @Autowired
    private ClinicsService clinicsService;

    /**
     * Customers age distribution
     *
     * @return an array with the number of customers for each age interval
     */
    public int[] getCustomersAgeDistribution() {
        //Maximum age 110
        int[] distribution = new int[11];
        LocalDate now = LocalDate.now();
        List<Customer> customers = customersService.getAllCustomers();
        for (Customer customer : customers) {
            Period customerAge = Period.between(customer.getDob(), now);
            int ageDecade = customerAge.getYears() / 10;
            distribution[ageDecade]++;
        }
        return distribution;
    }

    /**
     * A customer is considered as 'discontent' if after 1/3 appointment
     * does not return. (the value of months after which a customer is
     * considered as not returning is defined into the clinic settings
     *
     * Customers dissatisfied
     *
     * @return a list of customers that are considered as discontent
     * given the parameters discussed
     */
    public List<Customer> getDiscontentCustomers() {
        List<AdvancedCustomer> customers = advancedCustomerService.getAdvancedCustomers();
        List<Customer> discontentCustomers = new ArrayList<>();
        for (AdvancedCustomer customer : customers) {
            List<Appointment> appointments = customer.getAppointments();
            if (appointments.size()< 4 && appointments.size()>0) {
                Appointment last = appointments.get(appointments.size() - 1);
                if (last.getDate().plusMonths(clinicsService.getClinic().getMonthPeriodBreak()).isBefore(LocalDate.now())) {
                    discontentCustomers.add(last.getCustomer());
                    System.out.println(customer.getCustomer().getFirstName() + "is discontent: " + discontentCustomers.size());
                }
            }
        }
        return discontentCustomers;
    }

    /**
     * Referral type count
     *
     * @return the number of customer for each type of referral
     */
    public HashMap<String, Integer> getReferralInfluence() {
        List<Customer> customers = customersService.getAllCustomers();
        HashMap<String, Integer> referral = new HashMap<>();
        referral.put("online", 0);
        referral.put("event", 0);
        referral.put("recommendation", 0);
//        for each customer the type of referral is read and the map is updated
        customers.forEach(customer -> {
            int value = referral.get(customer.getRefType());
            referral.put(customer.getRefType(), ++value);
        });
        return referral;
    }

    /**
     * Filling rates a specific week
     *
     * @param customerId - id of the customer
     * @return the injuries relative to to the customer and the
     * relatives. This give an indication if there are injuries that
     * are related to "genetics" factors
     */
    public HashMap<Injury, Integer> getRelativesInjuriesOrder(String customerId) {
        Customer patient = customersService.getCustomerById(customerId);
        List<Customer> relatives = patient.getCloseRelatives();
        relatives.add(patient);
        List<Appointment> relativeAppointments = new ArrayList<>();
        for (Customer customer : relatives) {
            relativeAppointments.addAll(appointmentsService.getAppointmentsByCustomer(customer));
        }
        HashMap<Injury, Integer> injuriesCount = new HashMap<>();
        for (Appointment appointment : relativeAppointments) {
            Injury injury = appointment.getInjury();
            if (!injuriesCount.containsKey(injury)) {
                injuriesCount.put(injury, 1);
            } else {
                Integer value = injuriesCount.get(injury);
                injuriesCount.replace(injury, ++value);
            }
        }
        return injuriesCount;
    }
}
