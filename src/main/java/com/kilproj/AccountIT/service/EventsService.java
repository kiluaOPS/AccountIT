package com.kilproj.AccountIT.service;

import com.kilproj.AccountIT.Repositories.EventsRepository;
import com.kilproj.AccountIT.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventsService {

    @Autowired
    private EventsRepository eventsRepository;

    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    public void saveAll(List<Event> events) {
        eventsRepository.saveAll(events);
    }

    public Event getEventById(Long id) {
        return eventsRepository.getEventById(id);
    }

    public List<Event> getAllEvents() {
        return (ArrayList) eventsRepository.findAll();
    }

    public void save(Event event) {
        eventsRepository.save(event);
    }

    public void deleteEvent(Event event) {
        eventsRepository.delete(event);
    }
}
