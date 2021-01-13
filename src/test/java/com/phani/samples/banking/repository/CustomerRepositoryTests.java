package com.phani.samples.banking.repository;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.model.Customer;
import com.phani.samples.banking.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Transactional
@SpringBootTest
public class CustomerRepositoryTests {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private CustomerService customerService;

  @Test
  public void linkAccounts() {
    Customer customer = new Customer();
    customer.setFirstName("phani");
    customerRepository.save(customer);

    Account account = new Account();
    account.setAccountNumber("12345");
    accountRepository.save(account);

    Customer customerInDb = customerRepository.findById(customer.getId()).get();
    assertThat(customerInDb).isNotNull();
    assertThat(customerInDb.getAccounts()).isEmpty();

    customerService.linkAccount(customer.getId(), account.getId());

    customerInDb = customerRepository.findById(customer.getId()).get();
    assertThat(customerInDb.getAccounts()).containsExactly(account);

    Account account2 = new Account();
    account2.setAccountNumber("45678");
    accountRepository.save(account2);

    customerService.linkAccount(customer.getId(), account2.getId());

    customerInDb = customerRepository.findById(customer.getId()).get();
    assertThat(customerInDb.getAccounts()).containsExactlyInAnyOrder(account, account2);
  }
}

//TODO - write negative tests
