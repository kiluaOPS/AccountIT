package com.kilproj.AccountIT.service.statistics;

import com.kilproj.AccountIT.DTO.AppointmentsList;
import com.kilproj.AccountIT.domain.AdvancedCustomer;
import com.kilproj.AccountIT.Repositories.AppointmentsRepository;
import com.kilproj.AccountIT.Repositories.CustomersRepository;
import com.kilproj.AccountIT.service.AdvancedCustomerService;
import com.kilproj.AccountIT.service.ClinicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class StatisticsService {

    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private AdvancedCustomerService advancedCustomerService;

    @Autowired
    private ClinicsService clinicsService;

    /**
     * Calculate te average length of a set of appointments. (appointments that are less than
     * 4 months apart). This length gives an idea of how long it takes for the practitioner
     * to "fix" the injury.
     * @return average number of treatments
     */
    public double getAverageTreatmentSet() {
        List<AdvancedCustomer> customers = advancedCustomerService.getAdvancedCustomers();
        double totalAppointments = 0.0;
        int count = 0;
        for (AdvancedCustomer customer : customers) {
            List<AppointmentsList> appointmentsGroups = customer.getAppointmentsSets();
            if (appointmentsGroups.size() > 1) {
                for (AppointmentsList appointmentsGroup : appointmentsGroups) {
                    count++;
                    totalAppointments += appointmentsGroup.size();
                }
            }
        }
        return totalAppointments/count;
    }


    private boolean datesValidDistance(LocalDate previous, LocalDate next) {
        long daysBetween = DAYS.between(previous, next);
//        System.out.println("previous: " + previous + " next: " + next + " time between: " + daysBetween);
        return daysBetween < clinicsService.getClinic().getMaxNumberOfDays();
    }
}
