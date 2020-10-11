package com.kilproj.AccountIT.controllers;

import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.exception.AppointmentException;
import com.kilproj.AccountIT.exception.CustomerException;
import com.kilproj.AccountIT.exception.InjuryException;
import com.kilproj.AccountIT.service.AppointmentsService;
import com.kilproj.AccountIT.service.CustomersService;
import com.kilproj.AccountIT.service.InjuriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentsController {
    /* Controller for all operations on Appointment/s
     * Basic CRUD operations
     */

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private InjuriesService injuriesService;

    @Autowired
    private CustomersService customersService;

    /* Return all appointments in database
     */
    @GetMapping("/appointments")
    public ResponseEntity<?> getAppointments() {
        return new ResponseEntity<>(appointmentsService.getAllAppointments(), HttpStatus.OK);
    }

    /* Create appointment in database
     */
    @PostMapping("/appointment")
    public ResponseEntity<?> createAppointment(@Valid @RequestBody Appointment appointment, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Invalid Appointment Object detected", HttpStatus.BAD_REQUEST);
        }
        try {
            //to generate the relations in the appointment object the entities need to be returned from the database
            appointment.setInjury(injuriesService.getInjuryByType(appointment.getInjury().getType()));
            appointment.setCustomer(customersService.getCustomerById(appointment.getCustomer().getId()));
        } catch (InjuryException e) {
            return new ResponseEntity<>("Injury Not Found", HttpStatus.BAD_REQUEST);
        }
        appointmentsService.save(appointment);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }
    /* Delete appointment in database
     */
    @DeleteMapping(value = "appointments/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("id") String id) {
        try {
            Appointment appointment = appointmentsService.deleteAppointmentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (AppointmentException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /* Get  appointment by ID from database
     */
    @GetMapping(value = "/appointment/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable("id") String id) {
        Appointment appointment = appointmentsService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    /* Modify appointment
     */
    @PutMapping("/appointment")
    public ResponseEntity<?> updateAppointment(@Valid @RequestBody Appointment appointment, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Invalid Customer Object detected", HttpStatus.BAD_REQUEST);
        }
        appointmentsService.updateAppointment(appointment);
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
    }
}
