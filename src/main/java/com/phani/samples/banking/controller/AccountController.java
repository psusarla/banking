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

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

  //TODO validation of input fields

  @Autowired
  AccountService accountService;

  @Autowired
  StatementService statementService;

  @GetMapping
  public List<Account> getAllAccounts() {
     return accountService.getAllAccounts();
  }

  @GetMapping("/{id}")
  public Account getAccount(long id) {
    return accountService.getAccount(id);
  }

  //TODO - pdf looks ugly, I apologize for that
  @GetMapping("/{id}/statement")
  public ResponseEntity<byte[]> getStatement(Long id) throws Exception { //TODO - should not throw exception
    ByteArrayInputStream byteArrayInputStream =  statementService.generateTablePdf();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    String filename = "statement.pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    ResponseEntity<byte[]> response = new ResponseEntity<>(byteArrayInputStream.readAllBytes(), headers, HttpStatus.OK);
    return response;
  }

  @PutMapping("/{id}")
  public void updateAccount(@PathVariable long id, @RequestBody Account account) {
    accountService.updateAccount(id, account);
  }

  @PostMapping
  public void addAccount(@RequestBody Account account) {
    accountService.addAccount(account);
  }

  @DeleteMapping("/{id}")
  public void deleteAccount(@PathVariable long id) {
    accountService.deleteAccount(id);
  }
}
