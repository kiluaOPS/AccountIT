package com.kilproj.AccountIT.controllers;

import com.kilproj.AccountIT.domain.Clinic;
import com.kilproj.AccountIT.service.ClinicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClinicController {
    /* Controller for all operations on Clinic.
     * Clinic is a Singleton, as such there is no option
     * for creating one but only read it or modify it
     */

    @Autowired
    private ClinicsService clinicsService;

    /* Get Clinic entity or create one if does not exist
     */
    @GetMapping("/clinic")
    public ResponseEntity<?> getClinics() {
        Clinic clinic = clinicsService.getClinic();
        return new ResponseEntity<>(clinic, HttpStatus.OK);
    }

    /* Modify Clinic entity
     */
    @PutMapping("/update-clinic")
    public ResponseEntity<?> createClinic(@Valid @RequestBody Clinic clinic) {
        clinicsService.modifyClinic(clinic);
        return new ResponseEntity<>(clinic, HttpStatus.CREATED);
    }
}

