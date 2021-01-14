package com.phani.samples.banking.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class InterestService {

  //TODO - make variables private everywhere
  //TODO - Change to runtime exceptions everywhere

  @Autowired
  private AccountRepository accountRepository;

  public double calculateInterest(double currentBalance) {
    double interestGained =  currentBalance * (3.5 /(100));
    return interestGained;
  }

   //  In a production system, Quartz or Shedlock (https://www.baeldung.com/shedlock-spring) should be used to avoid every instance of the api performing this calculation and update.
  @Scheduled(cron = "${interest.calculator.schedule:0 0 0 25 12 ?}") //25th December of every year
  public void updateBalance() {
    List<Account> accountList = (List<Account>) accountRepository.findAll();
    for (Account account : accountList) {
      double currentBalance = account.getCurrentBalance();
      double interestEarned = calculateInterest(currentBalance);
      currentBalance = (double) Math.round((currentBalance + interestEarned) * 100) / 100;
      account.setCurrentBalance(currentBalance);
      accountRepository.save(account);
    }
  }
}
