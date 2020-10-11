package com.kilproj.AccountIT.domain;

import com.kilproj.AccountIT.DTO.AppointmentsList;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class AdvancedCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Appointment> appointments = new ArrayList<>();

    @Transient
    private List<AppointmentsList> appointmentsSets = new ArrayList<>();

    public AdvancedCustomer(Customer customer) {
        this.customer = customer;
    }

    /* Empty constructor necessary for JPA calls */
    public AdvancedCustomer() {
    }


    public AdvancedCustomer(Customer customer, List<Appointment> list) {
        this.customer = customer;
        this.appointments = list;
        Collections.sort(this.appointments);
    }

    public AdvancedCustomer(List<Appointment> list) {
        this.appointments = list;
        if (list.size() > 0) {
            this.customer = list.get(0).getCustomer();
        }
        Collections.sort(this.appointments);
    }

    public void addAppointments(List<Appointment> appointments) {
        if (customer == null && appointments.size() > 0) {
            this.customer = appointments.get(0).getCustomer();
        }
        this.appointments.addAll(appointments);
        Collections.sort(this.appointments);
    }

    public void removeAppointments(Appointment appointmentsToRemove, int maxNumberOfDays) {
        this.appointments.remove(appointmentsToRemove);
        this.applyDateInjuryFilter(maxNumberOfDays);
    }

    public void removeAppointments(List<Appointment> appointmentsToRemove, int maxNumberOfDays) {
        this.appointments.removeAll(appointmentsToRemove);
        this.applyDateInjuryFilter(maxNumberOfDays);
    }

    /* Apply filtering and creates clusters of appointments */
    public List<AppointmentsList> applyDateInjuryFilter(int maxNumberOfDays) {
        List<AppointmentsList> splitList = new ArrayList<>();
        if (this.appointments.size() > 0) {
            List<AppointmentsList> appointmentsSets = applyDateClusterization(maxNumberOfDays);
            for (AppointmentsList appointmentsList : appointmentsSets) {
                List<Appointment> appointments = appointmentsList.getAppointments();
                List<AppointmentsList> split = splitByInjury(appointments);
                splitList.addAll(split);
            }
            this.appointmentsSets = splitList;
        }
        return splitList;
    }

    /* Split list of appointment in smaller list of appointments with
     * that are related to the same injury
     */
    private List<AppointmentsList> splitByInjury(List<Appointment> appointments) {
        HashMap<Injury, List<Appointment>> injuryMap = new HashMap<>();
        for (Appointment appointment : appointments) {
            Injury injury = appointment.getInjury();
            if (injuryMap.containsKey(injury)) {
                List<Appointment> temp = injuryMap.get(injury);
                temp.add(appointment);
                injuryMap.replace(injury, temp);
            } else {
                List<Appointment> temp = new ArrayList<>();
                temp.add(appointment);
                injuryMap.put(injury, temp);
            }
        }
        List<AppointmentsList> output = new ArrayList<>();
        injuryMap.forEach((injury, finalAppointments) -> {
            output.add(new AppointmentsList(finalAppointments));
        });
        return output;
    }

    /**
     * Given a set of appointments belonging to a customer it divides the appointments in
     * clusters (each cluster has appointments that are less than 4 months apart)
     */
    private List<AppointmentsList> applyDateClusterization(int maxNumberOfDays) {
        Appointment previous = appointments.get(0);
        int count = 0;
        List<AppointmentsList> appointmentsSets = new ArrayList<>();
        appointmentsSets.add(new AppointmentsList());
        for (int i = 0; i < appointments.size(); i++) {
            if (i > 0) {
                previous = appointments.get(i - 1);
            }
            Appointment next = appointments.get(i);
            if (datesValidDistance(previous.getDate(), next.getDate(), maxNumberOfDays)) {
                appointmentsSets.get(count).addAppointmentChrono(next);
            } else {
                count++;
                AppointmentsList appointmentsList = new AppointmentsList();
                appointmentsList.addAppointmentChrono(next);
                appointmentsSets.add(appointmentsList);
            }
        }
        return appointmentsSets;
    }

    private boolean datesValidDistance(LocalDate previous, LocalDate next, int maxNumberOfDays) {
        long daysBetween = DAYS.between(previous, next);
        return daysBetween < maxNumberOfDays;
    }

    /* Getters and Setters necessary for JPA */

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        Collections.sort(this.appointments);
    }

    public List<AppointmentsList> getAppointmentsSets() {
        return appointmentsSets;
    }

    public void setAppointmentsSets(List<AppointmentsList> appointmentsSets) {
        this.appointmentsSets = appointmentsSets;
    }

    @Override
    public String toString() {
        String message = "AdvancedCustomer :" + customer + "\n";
        return message;
    }

}
