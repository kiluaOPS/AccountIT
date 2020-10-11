package com.kilproj.AccountIT.domain;

import com.kilproj.AccountIT.service.ClinicsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Clinic Name can not be empty")
    private String clinicName;

    /*Number of appointments in a week
     */
    private int averageWeekSlots = 42;

    /* for discontent customer
     */
    private int monthPeriodBreak = 3;

    /* Number of months from event (filtering of Customer)
     */
    private int timeFromEvent = 1;

    /* Max number of days between two appointments to be considered in the
     *  same group.
     */
    private int maxNumberOfDays = 90;

    @Transient
    @Autowired
    private ClinicsService clinicsService;

    public Clinic() {
    }

    public Clinic(String clinicName, Long id) {
        this.clinicName =clinicName;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public int getMonthPeriodBreak() {
        return monthPeriodBreak;
    }

    public void setMonthPeriodBreak(int monthPeriodBreak) {
        this.monthPeriodBreak = monthPeriodBreak;
    }

    public int getMaxNumberOfDays() {
        return maxNumberOfDays;
    }

    public void setMaxNumberOfDays(int maxNumberOfDays) {
        this.maxNumberOfDays = maxNumberOfDays;
    }

    public int getAverageWeekSlots() {
        return averageWeekSlots;
    }

    public void setAverageWeekSlots(int averageWeekSlots) {
        this.averageWeekSlots = averageWeekSlots;
    }

    public int getTimeFromEvent() {
        return timeFromEvent;
    }

    public void setTimeFromEvent(int timeFromEvent) {
        this.timeFromEvent = timeFromEvent;
    }

    @Override
    public String toString() {
        return "Clinic{" +
                "id=" + id +
                ", clinicName='" + clinicName + "}";
    }
}
