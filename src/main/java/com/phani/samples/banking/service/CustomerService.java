package com.phani.samples.banking.service;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.model.Customer;
import com.phani.samples.banking.repository.AccountRepository;
import com.phani.samples.banking.repository.CustomerRepository;
import com.phani.samples.banking.request.KycRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(propagation= Propagation.REQUIRED)
@Service
public class CustomerService {
  //TODO transform request to entity objects

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  AccountRepository accountRepository;

  public List<Customer> getAllCustomers() {
    return (List<Customer>) this.customerRepository.findAll();
  }

  public Customer getCustomer(long id) {
    Optional<Customer> optionalCustomer = customerRepository.findById(id);
    if (optionalCustomer.isPresent()) {
      return optionalCustomer.get();
    } else {
      return null;
    }
  }

  public boolean addCustomer(Customer customer) {
    //Normally this function should take a value object as input, map it to an entity object and save
    return customerRepository.save(customer) != null;
  }

  public void deleteCustomer(long id) {
    customerRepository.deleteById(id);
  }

  public Customer updateKyc(long id, KycRequest kycRequest) {
    return customerRepository.findById(id)
            .map(cus -> {
              cus.setKycPhone(kycRequest.getKycPhone());
              cus.setKycProofOfAddress(kycRequest.getKycProofOfAddress());
              return customerRepository.save(cus);
            })
            .orElseThrow(() -> new RuntimeException("Customer " + id + " not found"));
  }

  public Customer updateCustomer(long id, Customer customer) {
    return customerRepository.findById(id)
            .map(cus -> {
              cus.setLastName(customer.getLastName());
              cus.setFirstName(customer.getFirstName());
              //TODO - KYC details
              return customerRepository.save(cus);
            })
            .orElseGet(() -> {
              customer.setId(id);
              return customerRepository.save(customer);
            });
  }

  public void linkAccount(long id, long accountId) {
    Optional<Customer> optionalCustomer = customerRepository.findById(id);
    if (optionalCustomer.isPresent()) {
       Customer customer = optionalCustomer.get();
       Optional<Account> optionalAccount = accountRepository.findById(accountId);
       if (optionalAccount.isPresent()) {
         Account account = optionalAccount.get();
/*         if (customer.getAccounts() )
         List<Account> accountList = new ArrayList<>();
         accountList.add(account); */
         customer.getAccounts().add(account);
       } else {
         throw new RuntimeException("Account " + accountId + " not found"); //TODO string append not good
       }
    } else {
      throw new RuntimeException("Customer " + id + " not found");
    }
  }
}
