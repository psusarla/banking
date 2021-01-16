package com.phani.samples.banking.service;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(propagation= Propagation.REQUIRED)
@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  EntityManager entityManager;

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
    //TODO - Normally this function should take a value object as input, map it to an entity object and save
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
              acc.setInterestEarned(account.getInterestEarned());
              acc.setType(account.getType());
              return accountRepository.save(acc);
            })
            .orElseGet(() -> {
              account.setId(id);
              return accountRepository.save(account);
            });
  }

  public void transferBalance(long fromAccountId, long toAccountId, double amount) {
      //TODO - We will be adding logs such as these through out the application as needed
      log.info("Attempting to transfer {} from account {} to account {}", amount, fromAccountId, toAccountId);
      Account fromAccount = entityManager.find(Account.class, fromAccountId);
      if (fromAccount == null) {
         throw new RuntimeException("Invalid account " + fromAccountId);
      }
      double fromAccountBalance = fromAccount.getCurrentBalance();
      if (fromAccountBalance < amount) {
        throw new RuntimeException("Insufficient Amount");
      }

      Account toAccount = entityManager.find(Account.class, toAccountId);
      if (toAccount == null) {
        throw new RuntimeException("Invalid account " + toAccountId);
      }

      entityManager.lock(fromAccount, LockModeType.OPTIMISTIC);
      entityManager.lock(toAccount, LockModeType.OPTIMISTIC);

      fromAccount.setCurrentBalance(fromAccountBalance - amount);
      accountRepository.save(fromAccount);

      double toAccountBalance = toAccount.getCurrentBalance();
      toAccount.setCurrentBalance(toAccountBalance + amount);
      accountRepository.save(toAccount);
  }
}
