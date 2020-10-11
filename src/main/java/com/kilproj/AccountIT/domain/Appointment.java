package com.kilproj.AccountIT.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kilproj.AccountIT.exception.DateException;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Appointment implements Comparable<Appointment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    //types = "1", "r"
    @NotBlank(message = "Type of treatment can not be empty")
    private String type;

    @ManyToOne
    private Injury injury;


    public Appointment(Customer customer, LocalDate date, String type, Injury injury) {
        this.customer = customer;
        this.date = date;
        this.type = type;
        this.injury = injury;
    }

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String stringDate) {
        try {
            this.date = parseDate(stringDate);
        } catch (DateException e ) {
            e.printStackTrace();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Injury getInjury() {
        return injury;
    }

    public void setInjury(Injury injury) {
        this.injury = injury;
    }

    @Override
    public String toString() {
        String message = "";
        if (customer != null) {
            message = "* id" + id +
                    " name=" + customer.getFirstName() +
                    " inj=" + injury.getType() +
                    " date=" + date + " *";
        } else {
            message = "* id" + id +
                    " name= no customer assigned" +
                    " inj=" + injury.getType() +
                    " date=" + date + " *";
        }
        return message;
    }

    private LocalDate parseDate(String dateString) throws DateException {
        SimpleDateFormat[] formats = new SimpleDateFormat[]
                {new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("d-MMM-yy"),
                        new SimpleDateFormat("d-MMM-yyyy"), new SimpleDateFormat("dd-MMM-yyyy"),
                        new SimpleDateFormat("dd/MM/yyyy")};

        Date parsedDate = null;

        for (int i = 0; i < formats.length; i++) {
            try {
                parsedDate = formats[i].parse(dateString);
                return parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                continue;
            }
        }
        throw new DateException("Unknown date format: '" + dateString + "'");
    }


    @Override
    public int compareTo(Appointment o) {
        return this.date.compareTo(o.date);
    }
}
