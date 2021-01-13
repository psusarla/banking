package com.phani.samples.banking.controller;

import com.phani.samples.banking.model.Customer;
import com.phani.samples.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  //TODO validation of input fields

  @Autowired
  CustomerService customerService;

  @GetMapping
  public List<Customer> getAllCustomers() {
     return customerService.getAllCustomers();
  }

  @GetMapping("/{id}")
  public Customer getCustomer(long id) {
    return customerService.getCustomer(id);
  }

  @PutMapping("/{id}")
  public void updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
    customerService.updateCustomer(id, customer);
  }

  @PutMapping("/{id}/account/{accountId}")
  public void updateCustomer(@PathVariable long id, @PathVariable long accountId) {
    customerService.linkAccount(id, accountId);
  }

  @PostMapping
  public void addCustomer(@RequestBody Customer customer) {
    customerService.addCustomer(customer);
  }

  @DeleteMapping("/{id}")
  public void deleteCustomer(@PathVariable long id) {
    customerService.deleteCustomer(id);
  }
}
