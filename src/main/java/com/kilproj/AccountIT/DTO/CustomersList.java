package com.kilproj.AccountIT.DTO;
import com.kilproj.AccountIT.domain.Customer;


import javax.validation.Valid;
import java.util.List;

public class CustomersList {

    @Valid
    private List<Customer> customers;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "CustomersList{" +
                "customers=" + customers +
                '}';
    }
}


