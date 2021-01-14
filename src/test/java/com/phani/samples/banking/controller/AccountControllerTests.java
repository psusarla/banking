package com.phani.samples.banking.controller;

import com.phani.samples.banking.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class AccountControllerTests {

  @Autowired
  private AccountController accountController;

  @Test
  public void getStatement() throws Exception {
    ResponseEntity<byte[]> responseEntity = accountController.getStatement(1l);
    assertThat(responseEntity).isNotNull();
    byte[] bytes = responseEntity.getBody();
    assertThat(bytes).isNotEmpty();
  }
}
