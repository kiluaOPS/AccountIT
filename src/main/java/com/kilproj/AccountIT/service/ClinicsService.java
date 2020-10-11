package com.kilproj.AccountIT.service;

import com.kilproj.AccountIT.Repositories.ClinicsRepository;
import com.kilproj.AccountIT.domain.Clinic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClinicsService {

    @Autowired
    private ClinicsRepository clinicsRepository;

    public ClinicsService() {
    }

    public Clinic getClinic() {
        Clinic clinicSingleton = null;
        Optional<Clinic> clinic = clinicsRepository.findById((long)1);
        try {
            clinicSingleton = clinic.get();
        } catch (NoSuchElementException e) {
            clinicSingleton = new Clinic("Loudoun", (long)1);
            clinicsRepository.save(clinicSingleton);
        }
        return clinicSingleton;
    }

    public Clinic modifyClinic(Clinic clinic) {
        clinicsRepository.save(clinic);
        return clinic;
    }
}
