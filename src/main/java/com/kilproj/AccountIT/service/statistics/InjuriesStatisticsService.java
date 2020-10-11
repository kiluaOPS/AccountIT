package com.kilproj.AccountIT.service.statistics;

import com.kilproj.AccountIT.DTO.AppointmentsList;
import com.kilproj.AccountIT.domain.AdvancedCustomer;
import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.domain.Injury;
import com.kilproj.AccountIT.exception.CustomerException;
import com.kilproj.AccountIT.service.*;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class InjuriesStatisticsService {

    @Autowired
    private CustomersService customersService;

    @Autowired
    private AdvancedCustomerService advancedCustomerService;

    @Autowired
    private InjuriesService injuriesService;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private ClinicsService clinicsService;

    public AdvancedCustomer getAdvancedCustomerById(String id) throws CustomerException {
        List<AdvancedCustomer> customers = advancedCustomerService.getAdvancedCustomers();
        for (AdvancedCustomer advancedCustomer : customers) {
            if (advancedCustomer.getCustomer().getId() == Long.parseLong(id)) {
                advancedCustomer.applyDateInjuryFilter(clinicsService.getClinic().getMaxNumberOfDays());
                return advancedCustomer;
            }
        }
        throw new CustomerException("Customer Not Found");
    }


    /**
     * Get injury-age recovery influence
     *
     * @return a map. Integer key represent the age interval, the value is another map
     * containing the average number of appointments to recover for each injury
     */
    public HashMap<Integer, HashMap<Injury, Double[]>> getInjuryAgeRecoveryInfluence() {
        List<AdvancedCustomer> customers = advancedCustomerService.getAdvancedCustomers();
        HashMap<Integer, HashMap<Injury, Double[]>> ageInjuryStatisticsMap = new HashMap<>();
        //Update appointments set subdivision.
        for (AdvancedCustomer advancedCustomer : customers) {
            advancedCustomer.applyDateInjuryFilter(clinicsService.getClinic().getMaxNumberOfDays());
        }
        //divide customer by age
        HashMap<Integer, List<AdvancedCustomer>> customerAgeMap = divideByAge(customers);
        HashMap<Integer, HashMap<Injury, List<AppointmentsList>>> ageInjuryMap = new HashMap<>();
        //for each list of of customers divided by age get the average n# appointmetns to recovery per injury
        customerAgeMap.forEach((integer, customerAdvancedList) -> {
            HashMap<Injury, List<AppointmentsList>> injuryMap = getSetsDividedByInjuries(customerAdvancedList);
            HashMap<Injury, Double[]> injuryStatisticsMap = new HashMap<>();
            injuryMap.forEach((injury, appointmentsLists) -> {
                injuryStatisticsMap.put(injury, getStandardDeviation(appointmentsLists));
            });

            // apply standard deviation filter
            HashMap<Injury, List<AppointmentsList>> mapAfterStandardDeviation = applyStandardDeviationFilter(injuryStatisticsMap, injuryMap);
            mapAfterStandardDeviation.forEach((injury, appointmentsLists) -> {
                injuryStatisticsMap.replace(injury, getStandardDeviation(appointmentsLists));
            });
            ageInjuryMap.put(integer, mapAfterStandardDeviation);

            // if no customer are present in the list do not add to the map
            boolean hasEntry = false;
            for (Map.Entry<Injury, Double[]> entry : injuryStatisticsMap.entrySet())
                if (entry.getValue()[0] != 0) {
                    hasEntry = true;
                    break;
                }
            if (hasEntry) {
                ageInjuryStatisticsMap.put(integer, injuryStatisticsMap);
            }
        });

        return ageInjuryStatisticsMap;
    }

    public HashMap<Integer, List<AdvancedCustomer>> divideByAge(List<AdvancedCustomer> customers) {
        LocalDate now = LocalDate.now();
        HashMap<Integer, List<AdvancedCustomer>> customerAgeMap = new HashMap<>();
        for (AdvancedCustomer customer : customers) {
            Period customerAge = Period.between(customer.getCustomer().getDob(), now);
            int ageDecade = customerAge.getYears() / 10;
            if (customerAgeMap.containsKey(ageDecade)) {
                customerAgeMap.get(ageDecade).add(customer);
            } else {
                List<AdvancedCustomer> advancedCustomerList = new ArrayList<>();
                advancedCustomerList.add(customer);
                customerAgeMap.put(ageDecade, advancedCustomerList);
            }
        }
        return customerAgeMap;
    }

    /**
     * Get injury recovery time
     *
     * @return a map containing the average number of appointments to recover for each injury
     */
    public HashMap<Injury, Double[]> getInjuryMeanRecoveryTime() {
        List<AdvancedCustomer> customers = advancedCustomerService.getAdvancedCustomers();
        for (AdvancedCustomer advancedCustomer : customers) {
            advancedCustomer.applyDateInjuryFilter(clinicsService.getClinic().getMaxNumberOfDays());
        }
        HashMap<Injury, List<AppointmentsList>> injuryMap = getSetsDividedByInjuries(customers);
        HashMap<Injury, Double[]> injuryStatisticsMap = new HashMap<>();
        injuryMap.forEach((injury, appointmentsLists) -> {
            injuryStatisticsMap.put(injury, getStandardDeviation(appointmentsLists));
        });
        HashMap<Injury, List<AppointmentsList>> mapAfterStandardDeviation = applyStandardDeviationFilter(injuryStatisticsMap, injuryMap);
        mapAfterStandardDeviation.forEach((injury, appointmentsLists) -> {
            injuryStatisticsMap.replace(injury, getStandardDeviation(appointmentsLists));
        });
        return injuryStatisticsMap;
    }


    private List<AppointmentsList> appointmentListDateValidator(List<AppointmentsList> appointmentsLists) {
        List<AppointmentsList> toBeRemoved = new ArrayList<>();
        for (AppointmentsList appointmentsList : appointmentsLists) {
            List<Appointment> appointments = appointmentsList.getAppointments();
            Appointment previous = appointments.get(0);
            for (Appointment appointment : appointments) {
                Appointment next = appointment;
                if (!datesValidDistance(previous.getDate(), next.getDate())) {
                    long daysBetween = DAYS.between(previous.getDate(), next.getDate());
                    toBeRemoved.add(appointmentsList);
                    break;
                }
                previous = next;
            }
        }
        appointmentsLists.removeAll(toBeRemoved);
        return appointmentsLists;
    }

    private boolean datesValidDistance(LocalDate previous, LocalDate next) {
//        days validator
        int validator = 90;
        long daysBetween = DAYS.between(previous, next);
        return daysBetween < validator;
    }

    private HashMap<Injury, List<AppointmentsList>> applyStandardDeviationFilter(HashMap<Injury, Double[]> injuryStatisticsMap,
                                                                                 HashMap<Injury, List<AppointmentsList>> injuryAppointmentMap) {
        injuryStatisticsMap.forEach((injury, aDoubleArray) -> {
            List<AppointmentsList> toBeRemoved = new ArrayList<>();
            double lowBoundary = aDoubleArray[0] - 2 * aDoubleArray[1];
            double highBoundary = aDoubleArray[0] + 2 * aDoubleArray[1];
            for (AppointmentsList appointments : injuryAppointmentMap.get(injury)) {
                if (appointments.size() > highBoundary || appointments.size() < lowBoundary) {

                    toBeRemoved.add(appointments);
                }
            }
            injuryAppointmentMap.get(injury).removeAll(toBeRemoved);
        });
        return injuryAppointmentMap;
    }

    private HashMap<Injury, List<AppointmentsList>> getSetsDividedByInjuries(List<AdvancedCustomer> customers) {
        HashMap<Injury, List<AppointmentsList>> map = new HashMap<>();
        List<Injury> injuries = injuriesService.getAllInjuries();
        for (Injury injury : injuries) {
            map.put(injury, new ArrayList<>());
        }
        for (AdvancedCustomer customer : customers) {
            for (AppointmentsList appointments : customer.getAppointmentsSets()) {
                Injury injury = appointments.getAppointments().get(0).getInjury();
                map.get(injury).add(appointments);
            }
        }
        return map;
    }

    private Double[] getStandardDeviation(List<AppointmentsList> appointmentsSets) {
        double[] values = new double[appointmentsSets.size()];
        int count = 0;
        for (AppointmentsList appointments : appointmentsSets) {
            values[count] = appointments.size();
            count++;
        }
        StandardDeviation sd = new StandardDeviation();
        double mean = Arrays.stream(values).average().orElse(0);
        Double[] result = new Double[]{mean, sd.evaluate(values)};
        return result;
    }
}
