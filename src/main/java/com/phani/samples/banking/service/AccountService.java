package com.phani.samples.banking.service;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.model.Customer;
import com.phani.samples.banking.repository.AccountRepository;
import com.phani.samples.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(propagation= Propagation.REQUIRED)
@Service
public class AccountService {
  //TODO transform request to entity objects

  @Autowired
  AccountRepository accountRepository;

  public List<Account> getAllAccounts() {
    return (List<Account>) this.accountRepository.findAll();
  }

  public Account getAccount(long id) {
    Optional<Account> optionalAccount = accountRepository.findById(id);
    if (optionalAccount.isPresent()) {
      return optionalAccount.get();
    } else {
      return null;
    }
  }

  public boolean addAccount(Account account) {
    //Normally this function should take a value object as input, map it to an entity object and save
    return accountRepository.save(account) != null;
  }

  public void deleteAccount(long id) {
    accountRepository.deleteById(id);
  }

  public Account updateAccount(long id, Account account) {
    return accountRepository.findById(id)
            .map(acc -> {
              acc.setAccountNumber(account.getAccountNumber());
              acc.setBeginningBalance(account.getBeginningBalance());
              acc.setCurrentBalance(account.getCurrentBalance());
              //TODO - use isEmpty
              //TODO - is below logic ok?
              if ((account.getCustomers() != null) && !account.getCustomers().isEmpty())
                acc.setCustomers(account.getCustomers());
              acc.setInterestEarned(account.getInterestEarned());
              acc.setType(account.getType());
              return accountRepository.save(acc);
            })
            .orElseGet(() -> {
              account.setId(id);
              return accountRepository.save(account);
            });
  }
}
