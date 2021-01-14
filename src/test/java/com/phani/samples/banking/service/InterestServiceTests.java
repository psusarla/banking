package com.phani.samples.banking.service;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.repository.AccountRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class InterestServiceTests {

  @Autowired
  AccountRepository accountRepository;
  long accountId;

 // @BeforeTestExecution
  public void setup() {
    Account account = new Account();
    account.setCurrentBalance(10);
    account.setAccountNumber("2525252");
    accountRepository.save(account);
    accountId = account.getId();

  }

  @Disabled
  @Test
  public void interestRate() throws Exception{
    Thread.sleep(2000);
    Optional<Account> accountOptional = accountRepository.findById(accountId);
    assertThat(accountOptional.isPresent()).isEqualTo(true);
    assertThat(accountOptional.get().getCurrentBalance()).isGreaterThan(10);
  }
}
