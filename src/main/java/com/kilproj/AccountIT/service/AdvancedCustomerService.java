package com.kilproj.AccountIT.service;

import com.kilproj.AccountIT.Repositories.AdvancedCustomerRepository;
import com.kilproj.AccountIT.domain.AdvancedCustomer;
import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.domain.Customer;
import com.kilproj.AccountIT.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvancedCustomerService {

    @Autowired
    private CustomersService customersService;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private AdvancedCustomerRepository advancedCustomerRepository;

    public void save(AdvancedCustomer advancedCustomer) {
        advancedCustomerRepository.save(advancedCustomer);
    }

    public void saveAll(List<AdvancedCustomer> advancedCustomers) {
        advancedCustomerRepository.saveAll(advancedCustomers);
    }

    public List<AdvancedCustomer> getAdvancedCustomers() {
        return (List) advancedCustomerRepository.findAll();
    }

    public void updateAdvancedCustomers() {
        List<AdvancedCustomer> advancedCustomers = (List) advancedCustomerRepository.findAll();
        List<Customer> customers = customersService.getAllCustomers();
        List<Appointment> appointments = appointmentsService.getAllAppointments();
        for (Customer customer : customers) {
            AdvancedCustomer advancedCustomer = null;
            for (AdvancedCustomer advCust : advancedCustomers) {
                if (advCust.getCustomer().equals(customer)) {
                    advancedCustomer = advCust;
                }
            }
            List<Appointment> customerRelatedAppointments = new ArrayList();
            for (Appointment appointment : appointments) {

                if (appointment.getCustomer().equals(customer)) {
                    customerRelatedAppointments.add(appointment);

                }
            }
            appointments.removeAll(customerRelatedAppointments);
            advancedCustomer.setAppointments(customerRelatedAppointments);
            advancedCustomerRepository.save(advancedCustomer);
        }
    }

    public AdvancedCustomer getAdvancedCustomer(Customer customer) throws CustomerException {
        List<AdvancedCustomer> advancedCustomers = (List) advancedCustomerRepository.findAll();
        for (AdvancedCustomer advancedCustomer : advancedCustomers) {
            if (advancedCustomer.getCustomer().equals(customer)) {
                return advancedCustomer;
            }
        }
        throw new CustomerException("Advanced Customer Not Found");
    }

    public AdvancedCustomer deleteAdvanceCustomerByCustomer(Customer customer) throws CustomerException {
        List<AdvancedCustomer> advancedCustomers = (List) advancedCustomerRepository.findAll();
        for (AdvancedCustomer advancedCustomer : advancedCustomers) {
            if (advancedCustomer.getCustomer().equals(customer)) {
                advancedCustomerRepository.delete(advancedCustomer);
                return advancedCustomer;
            }
        }
        throw new CustomerException("Advanced Customer Not Found");
    }

}
