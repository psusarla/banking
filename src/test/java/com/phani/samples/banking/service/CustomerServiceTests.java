package com.phani.samples.banking.service;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.model.Customer;
import com.phani.samples.banking.repository.AccountRepository;
import com.phani.samples.banking.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Transactional
@SpringBootTest
public class CustomerServiceTests {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void testAddCustomer() {
    Customer customer = new Customer();
    customer.setFirstName("phani");
    customerRepository.save(customer);
  }

  @Test
  public void getAllCustomers() {
    List<Customer> customerList = (List<Customer>)customerRepository.findAll();

    //TODO - remove system.out
    for (Customer customer: customerList) {
      System.out.println(customer);
    }
  }

  @Test
  public void deleteCustomers() {
    customerRepository.deleteAll();
  }

  @Test
  public void deleteSingleCustomer() {
    Customer customer = new Customer();
    customer.setFirstName("phani");
    customerRepository.save(customer);

    long customerId = customer.getId();

    customerRepository.delete(customer);

    assertThat(customerRepository.findById(customerId)).isEmpty();
  }

  @Test
  public void crudOperationsCustomers() {
    Customer customer = new Customer();
    customer.setFirstName("phani");
    customerRepository.save(customer);

    List<Customer> customerList = (List<Customer>)customerRepository.findAll();
    for (Customer customer1: customerList) {
      System.out.println("Customer is " + customer1);
    }
  }

  @Test
  public void linkAccounts() {
    Customer customer = new Customer();
    customer.setFirstName("phani");
    customerRepository.save(customer);

    Account account = new Account();
    account.setAccountNumber("12345");
    accountRepository.save(account);


  }
}
