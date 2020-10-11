package com.kilproj.AccountIT.DTO;

import com.kilproj.AccountIT.domain.Appointment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class AppointmentsList {

    @Id
    private Long id;

    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    public AppointmentsList(List<Appointment> appointmentsConstructor) {
        for (Appointment appointment : appointmentsConstructor) {
            addAppointmentChrono(appointment);
        }
    }

    public AppointmentsList() {
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointmentChrono(Appointment appointment) {
        appointments.add(appointment);
        Collections.sort(this.appointments);
    }

    public int size() {
        return appointments.size();
    }

    @Override
    public String toString() {
        return "{" + appointments +
                '}';
    }
}