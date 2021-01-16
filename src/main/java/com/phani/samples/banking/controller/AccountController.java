package com.phani.samples.banking.controller;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.service.AccountService;
import com.phani.samples.banking.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private StatementService statementService;

  @GetMapping
  public List<Account> getAllAccounts() {
     return accountService.getAllAccounts();
  }

  @GetMapping("/{id}")
  public Account getAccount(@PathVariable Long id) {
    return accountService.getAccount(id);
  }

  //TODO - Pdf looks ugly, I apologize for that
  @GetMapping("/{id}/statement")
  public ResponseEntity<byte[]> getStatement(@PathVariable Long id) {
    ByteArrayInputStream byteArrayInputStream =  statementService.generateStatement(id);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    String filename = "statement.pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    ResponseEntity<byte[]> response = new ResponseEntity<>(byteArrayInputStream.readAllBytes(), headers, HttpStatus.OK);
    return response;
  }

  @PutMapping("/{id}")
  public void updateAccount(@PathVariable Long id, @RequestBody Account account) {
    accountService.updateAccount(id, account);
  }

  //TODO - @Valid should be added to all request objects. Did not do this at other places in the interest of time.
  @PostMapping
  public void addAccount(@Valid @RequestBody Account account) {
    accountService.addAccount(account);
  }

  @DeleteMapping("/{id}")
  public void deleteAccount(@PathVariable Long id) {
    accountService.deleteAccount(id);
  }

  @PutMapping("/transfer/from/{fromAccountId}/to/{toAccountId}")
  public void transferBalance(@PathVariable Long fromAccountId,@PathVariable Long toAccountId,
                               @RequestParam double amount) {
    if (amount <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount should be positive");
    }
    accountService.transferBalance(fromAccountId, toAccountId, amount);
  }
}