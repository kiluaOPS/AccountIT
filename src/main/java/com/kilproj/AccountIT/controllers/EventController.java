package com.kilproj.AccountIT.controllers;

import com.kilproj.AccountIT.domain.Event;
import com.kilproj.AccountIT.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

@CrossOrigin("http://localhost:4200")
public class EventController {
    /* Controller for all operations on Event/s
     * Basic CRUD operations
     */

    @Autowired
    private EventsService eventsService;


    /* Return all events in database
     */
    @GetMapping("/events")
    public List<Event> getEvents() {
        return (List) eventsService.getAllEvents();
    }


    /* Save new event in database
     */
    @PostMapping("/event")
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event event, BindingResult result) {
        System.out.println(event);
        if (result.hasErrors()) {
            return new ResponseEntity<>("Invalid Event Object detected", HttpStatus.BAD_REQUEST);
        }
        eventsService.save(event);
        return new ResponseEntity<Event>(event, HttpStatus.CREATED);
    }

    /* Delete event from database
     */
    @DeleteMapping("events/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") String id) {
        Event event = eventsService.getEventById(Long.parseLong(id));
        if (event != null) {
            eventsService.deleteEvent(event);
            return new ResponseEntity<>("Event with id: " + id + " deleted", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Event do not exist in the database", HttpStatus.NOT_FOUND);
        }
    }
}

