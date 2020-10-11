package com.kilproj.AccountIT.Repositories;

import com.kilproj.AccountIT.domain.Clinic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicsRepository extends CrudRepository<Clinic, Long> {
}
