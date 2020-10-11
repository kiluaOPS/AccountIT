package com.kilproj.AccountIT.Repositories;

import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.domain.WeatherDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentsRepository extends CrudRepository<Appointment, Long> {


    List<Appointment> findAllByDateBetween(LocalDate dateStart,
                                          LocalDate dateEnd);
}
