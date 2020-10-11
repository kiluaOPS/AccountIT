package com.kilproj.AccountIT.domain;


import com.fasterxml.jackson.annotation.*;
import com.google.gson.JsonObject;
import com.kilproj.AccountIT.exception.DateException;
import com.kilproj.AccountIT.geocoding.Geocoding;
import com.kilproj.AccountIT.geocoding.GeocodingException;
import com.kilproj.AccountIT.geocoding.GeocodingImpl;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer implements Comparable<Customer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First Name can not be empty")
    private String firstName;
    @NotBlank(message = "Last Name can not be empty")
    private String lastName;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @NotBlank(message = "Email can not be empty")
    private String email;
    @NotBlank(message = "Reference type can not be empty")
    private String refType;

    @JsonIgnore
    @ManyToMany
    private List<Customer> closeRelatives = new ArrayList<>();

    private float longitude;
    private float latitude;

    @NotBlank(message = "Address can not be empty")
    private String address;

    @Transient
    private Geocoding geocoding = new GeocodingImpl();

    public Customer(String firstName, String lastName, LocalDate dob, String address, String email, String refType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.setAddress(address);
        this.email = email;
        this.refType = refType;
    }

    public Customer(){}

    public Customer removeRelative(Customer relative) {
        this.closeRelatives.remove(relative);
        return relative;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        JsonObject jsonLoc = null;
        try {
            jsonLoc = geocoding.getLatLng(address);

            this.latitude = jsonLoc.get("lat").getAsFloat();
            this.longitude = jsonLoc.get("lng").getAsFloat();
        } catch (GeocodingException e) {
            e.printStackTrace();
        }
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(String stringDate) {
        try {
            this.dob = parseDate(stringDate);
        } catch (DateException e ) {
            e.printStackTrace();
        }
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void addRelative(Customer customer) {
        this.closeRelatives.add(customer);
    }

    public List<Customer> getCloseRelatives() {
        return closeRelatives;
    }

    public void setCloseRelatives(List<Customer> closeRelatives) {
        this.closeRelatives = closeRelatives;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                '}';
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
    public int compareTo(Customer o) {
        return this.lastName.compareTo(o.lastName);
    }
}
