package com.kilproj.AccountIT.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kilproj.AccountIT.domain.Appointment;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class BasicAppointment{

    @Id
    private Long id;

    private Long appointmentId;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private String type;
    private String clinicName;


    public BasicAppointment(Appointment appointment) {
        this.appointmentId = appointment.getId();
        this.firstName = appointment.getCustomer().getFirstName();
        this.lastName = appointment.getCustomer().getLastName();
        this.date = appointment.getDate();
        this.type = appointment.getType();
//        this.clinicName = appointment.getClinic().getClinicName();
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }
}
