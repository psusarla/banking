package com.phani.samples.banking.controller;

import com.phani.samples.banking.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class CustomerControllerTests {

  @Autowired
  private CustomerController customerController;

  //Combing all crud operations into a single test as these are simple
 // @Transactional
  @Test
  public void allCrudOperations() {
    Customer customer = new Customer();
    customer.setFirstName("phani");
    customer.setLastName("kumar");
    customerController.addCustomer(customer);
    Customer customerInDb = customerController.getCustomer(customer.getId());
    assertThat(customerInDb).isEqualTo(customer);

    customer.setLastName("susarla");
    customerController.updateCustomer(customer.getId(),customer);

    customerInDb = customerController.getCustomer(customer.getId());
    assertThat(customerInDb).isEqualTo(customer);
    assertThat(customerInDb.getLastName()).isEqualTo("susarla");

    customerController.deleteCustomer(customer.getId());
    customerInDb = customerController.getCustomer(customer.getId());
    assertThat(customerInDb).isNull();
  }
}
