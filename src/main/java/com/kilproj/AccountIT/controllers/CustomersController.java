package com.kilproj.AccountIT.controllers;

import com.kilproj.AccountIT.domain.Customer;
import com.kilproj.AccountIT.exception.CustomerException;
import com.kilproj.AccountIT.service.AdvancedCustomerService;
import com.kilproj.AccountIT.service.AppointmentsService;
import com.kilproj.AccountIT.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomersController {
    /* Controller for all operations on Customer/s
     * Basic CRUD operations
     */

    @Autowired
    private CustomersService customersService;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private AdvancedCustomerService advancedCustomerService;


    /* Get all customers from database
     */
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customersService.getAllCustomers();
    }

    /* Get Customer by ID parameter
     */
    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") String id) {
        Customer customer = customersService.getCustomerById(id);
        if (customer == null) {
            return new ResponseEntity<>("Customer do not exist in the database", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    /* Save new customer in database
     */
    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        //if customer object passed is not in a valid format return error
        if (result.hasErrors()) {
            return new ResponseEntity<>("Invalid Customer Object detected", HttpStatus.BAD_REQUEST);
        }
        customersService.save(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    /* Modify existing customer
     */
    @PutMapping("/customer")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        //if customer object passed is not in a valid format return error
        if (result.hasErrors()) {
            return new ResponseEntity<>("Invalid Customer Object detected", HttpStatus.BAD_REQUEST);
        }
        customersService.update(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    /* Delete customer
     */
    @DeleteMapping(value = "customers/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") String id) {
        Customer customer = customersService.getCustomerById(id);
        try {
            //advanced customer associated to Customer is deleted.
            advancedCustomerService.deleteAdvanceCustomerByCustomer(customer);
            customersService.deleteCustomerById(id);
            return new ResponseEntity<>("Customer with id: " + id + " deleted", HttpStatus.NO_CONTENT);
        } catch (CustomerException e) {
            return new ResponseEntity<>("Customer do not exist in the database", HttpStatus.NOT_FOUND);
        }
    }
}
