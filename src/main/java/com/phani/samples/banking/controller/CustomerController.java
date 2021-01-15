package com.phani.samples.banking.controller;

import com.phani.samples.banking.model.Customer;
import com.phani.samples.banking.request.KycRequest;
import com.phani.samples.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping
  public List<Customer> getAllCustomers() {
     return customerService.getAllCustomers();
  }

  @GetMapping("/{id}")
  public Customer getCustomer(Long id) {
    return customerService.getCustomer(id);
  }

  @PutMapping("/{id}")
  public void updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
    customerService.updateCustomer(id, customer);
  }

  @PutMapping("/{id}/kyc")
  public void updateKYC(@PathVariable Long id, @RequestBody KycRequest kycRequest) {
    customerService.updateKyc(id, kycRequest);
  }

  @PutMapping("/{id}/account/{accountId}")
  public void linkAccount(@PathVariable Long id, @PathVariable long accountId) {
    customerService.linkAccount(id, accountId);
  }

  @PostMapping
  public void addCustomer(@RequestBody Customer customer) {
    customerService.addCustomer(customer);
  }

  @DeleteMapping("/{id}")
  public void deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
  }
}