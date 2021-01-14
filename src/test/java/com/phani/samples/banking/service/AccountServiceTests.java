package com.phani.samples.banking.service;

import com.phani.samples.banking.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class AccountServiceTests {

  @Autowired
  AccountService accountService;

  @Test
  public void transferBalance() {
    Account account1 = new Account();
    account1.setCurrentBalance(100);
    account1.setAccountNumber("12345");
    accountService.addAccount(account1);

    Account account2 = new Account();
    account2.setCurrentBalance(200);
    account2.setAccountNumber("35353");
    accountService.addAccount(account2);

    accountService.transferBalance(account1.getId(), account2.getId(), 10);

    Account updatedAccount1 = accountService.getAccount(account1.getId());
    assertThat(updatedAccount1.getCurrentBalance()).isEqualTo(90);

    Account updatedAccount2 = accountService.getAccount(account2.getId());
    assertThat(updatedAccount2.getCurrentBalance()).isEqualTo(210);
  }

  //TODO - write negative tests
}
