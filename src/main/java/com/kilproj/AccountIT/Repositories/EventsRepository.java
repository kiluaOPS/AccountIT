package com.kilproj.AccountIT.Repositories;

import com.kilproj.AccountIT.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends CrudRepository<Event, Long> {
    public Event getEventById(Long id);
}
