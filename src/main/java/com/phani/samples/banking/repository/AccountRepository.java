package com.phani.samples.banking.repository;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}