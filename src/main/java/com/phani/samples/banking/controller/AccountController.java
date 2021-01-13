package com.phani.samples.banking.controller;

import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

  //TODO validation of input fields

  @Autowired
  AccountService accountService;

  @GetMapping
  public List<Account> getAllAccounts() {
     return accountService.getAllAccounts();
  }

  @GetMapping("/{id}")
  public Account getAccount(long id) {
    return accountService.getAccount(id);
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
