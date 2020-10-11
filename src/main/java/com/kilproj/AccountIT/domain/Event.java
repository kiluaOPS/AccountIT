package com.kilproj.AccountIT.domain;

import com.google.gson.JsonObject;
import com.kilproj.AccountIT.geocoding.Geocoding;
import com.kilproj.AccountIT.geocoding.GeocodingException;
import com.kilproj.AccountIT.geocoding.GeocodingImpl;
import com.kilproj.AccountIT.tools.LocalDateDeserializer;
import com.kilproj.AccountIT.tools.LocalDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Event Name can not be empty")
    private String name;


    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @NotBlank(message = "Address can not be empty")
    @Size(min = 5, max = 7, message = "Postcode is usually 6 characters long")
    private String address;

    private float longitude;
    private float latitude;

    @Transient
    private Geocoding geocoding = new GeocodingImpl();

    public Event(String name, LocalDate date, String address) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.setAddress(address);
    }

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    /* toString modified to return the object with JSON format
     * Maps of object when sent through front-end use toString
     * for the object representation.
     */
    @Override
    public String toString() {
        return "{\"id\" : \"" + id + "\"" +
                ",\"name\" : \"" + name + "\"" +
                ",\"date\" : \"" + date + "\"" +
                ",\"address\" : \"" + address + "\"" +
                ",\"latitude\" : \"" + latitude + "\"" +
                ",\"longitude\" : \"" + longitude + "\"}";
    }
}
