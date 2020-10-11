package com.kilproj.AccountIT.Repositories;

import com.kilproj.AccountIT.domain.Injury;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InjuriesRepository extends CrudRepository<Injury, Long> {
}
