package com.kilproj.AccountIT.Repositories;

import com.kilproj.AccountIT.domain.WeatherDay;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends CrudRepository<WeatherDay, Long> {

    List<WeatherDay> findAllByDateBetween(LocalDate dateStart,
                                                     LocalDate dateEnd);
}
