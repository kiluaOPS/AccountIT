package com.kilproj.AccountIT.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Injury {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;

    public Injury(String type) {
        this.type = type.toLowerCase();
    }

    public Injury() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.toLowerCase();
    }

    @Override
    public String toString() {
        return "{\"type\" : \"" + type  + "\"}";
    }
}

