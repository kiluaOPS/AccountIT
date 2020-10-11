package com.kilproj.AccountIT.service;

import com.kilproj.AccountIT.Repositories.InjuriesRepository;
import com.kilproj.AccountIT.domain.Injury;
import com.kilproj.AccountIT.exception.InjuryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InjuriesService {

    @Autowired
    private InjuriesRepository injuriesRepository;

    public void saveAll(List<Injury> injuries) {
        injuriesRepository.saveAll(injuries);
    }

    public Injury getInjuryByType(String type) throws InjuryException {
        List<Injury> injuries = (List) injuriesRepository.findAll();
        for (Injury injury: injuries) {
            if (injury.getType().equals(type)) {
                return injury;
            }
        }
        throw  new InjuryException("Injury " + type +  "Not Found in the DB" );
    }

    public List<Injury> getAllInjuries() {
        return (List) injuriesRepository.findAll();
    }
}
