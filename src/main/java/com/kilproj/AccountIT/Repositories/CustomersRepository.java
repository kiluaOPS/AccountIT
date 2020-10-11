package com.kilproj.AccountIT.Repositories;

import com.kilproj.AccountIT.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends CrudRepository<Customer, Long> {
}
