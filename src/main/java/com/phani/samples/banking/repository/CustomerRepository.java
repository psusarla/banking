package com.phani.samples.banking.repository;

import com.phani.samples.banking.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
