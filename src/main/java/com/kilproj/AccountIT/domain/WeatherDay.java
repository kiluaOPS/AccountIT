package com.kilproj.AccountIT.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class WeatherDay implements Comparable<WeatherDay> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private int highTemp;

    private int lowTemp;

    public WeatherDay(LocalDate date, int highTemp, int lowTemp) {
        this.date = date;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }

    public WeatherDay() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }

    @Override
    public String toString() {
        return "WeatherDay{" +
                "id=" + id +
                ", date=" + date +
                ", highTemp=" + highTemp +
                ", lowTemp=" + lowTemp +
                '}';
    }

    @Override
    public int compareTo(WeatherDay o) {
        return this.date.compareTo(o.date);
    }
}
