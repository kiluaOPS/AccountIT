package com.kilproj.AccountIT.service;


import com.kilproj.AccountIT.DTO.CustomersList;
import com.kilproj.AccountIT.Repositories.CustomersRepository;
import com.kilproj.AccountIT.domain.AdvancedCustomer;
import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private AdvancedCustomerService advancedCustomerService;

    @Autowired
    private AppointmentsService appointmentsService;

    public List<Customer> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customersRepository.findAll();
        return customers;
    }

    public void saveAll(CustomersList customers) {
        customersRepository.saveAll(customers.getCustomers());
        for (Customer customer: customers.getCustomers()) {
            AdvancedCustomer advancedCustomer = new AdvancedCustomer(customer);
            advancedCustomerService.save(advancedCustomer);
        }
    }

    public void saveAll(List<Customer> customers) {
        customersRepository.saveAll(customers);
        for (Customer customer: customers) {
            AdvancedCustomer advancedCustomer = new AdvancedCustomer(customer);
            advancedCustomerService.save(advancedCustomer);
        }
    }

    public void save(Customer customer) {
        customersRepository.save(customer);
        AdvancedCustomer advancedCustomer = new AdvancedCustomer(customer);
        advancedCustomerService.save(advancedCustomer);
    }

    public void update(Customer customer) {
        customersRepository.save(customer);
    }

    public Customer getCustomerById(String customerId) {
        List<Customer> customers = (ArrayList<Customer>) customersRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getId() == Long.parseLong(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public Customer getCustomerById(Long customerId) {
        List<Customer> customers = (ArrayList<Customer>) customersRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public void deleteCustomerById(String customerId) {
        List<Customer> customers = (ArrayList<Customer>) customersRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getId() == Long.parseLong(customerId)) {
                List<Customer> relatives = customer.getCloseRelatives();
                for (Customer relative: relatives) {
                    relative.removeRelative(customer);
                    customersRepository.save(relative);
                }
                customersRepository.delete(customer);
            }
        }
    }


    public Customer getCustomer(String firstName, String lastName) {
        List<Customer> customers = (ArrayList<Customer>) customersRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getFirstName().equals(firstName)) {
                if (customer.getLastName().equals(lastName)) {
                    return customer;
                }
            }
        }
        return null;
    }

    /*
     * Gets the customers that have joined the clinic after the given date.
     * It uses type as check parameter. Could be done by looking at all
     * appointments and filtering.
     */
    public List<Customer> getCustomerJoinedAfterDate(LocalDate eventDate, LocalDate endDate) {
        List<Appointment> appointments = appointmentsService.getAppointmentsBetween(eventDate, endDate);
        List<Customer> customers = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getType().equals("1")) {
                customers.add(appointment.getCustomer());
            }
        }
        return customers;
    }

    /**
     * Check if a customer already exist give a first name and a last name
     */
    public boolean existByInfo(String firstName, String lastName) {
        List<Customer> customers = (List) customersRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getFirstName().equals(firstName)) {
                if (customer.getLastName().equals(lastName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
