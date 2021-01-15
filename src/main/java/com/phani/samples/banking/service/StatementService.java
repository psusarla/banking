package com.phani.samples.banking.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.phani.samples.banking.model.Account;
import com.phani.samples.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class StatementService {
  @Autowired
  private AccountRepository accountRepository;

  private void addTableHeader(PdfPTable table) {
    Stream.of("Beginning Balance", "Current Balance", "Interest Earned") //TODO - save interest earned in InterestService
            .forEach(columnTitle -> {
              PdfPCell header = new PdfPCell();
              header.setBackgroundColor(BaseColor.LIGHT_GRAY);
              header.setBorderWidth(2);
              header.setPhrase(new Phrase(columnTitle));
              table.addCell(header);
            });
  }

  private void addRows(PdfPTable table, Account account) {
    table.addCell(Double.toString(account.getBeginningBalance()));
    table.addCell(Double.toString(account.getCurrentBalance()));
    table.addCell(Double.toString(account.getInterestEarned()));
  }

  public ByteArrayInputStream generateStatement(Long id) {
    Optional<Account> accountOptional = accountRepository.findById(id);
    Account account = accountOptional.get();
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      Document document = new Document();
      PdfWriter.getInstance(document, out);
      document.open();
      document.add(new Paragraph("Account: " + account.getAccountNumber() + "\n\n"));
      PdfPTable table = new PdfPTable(3);
      addTableHeader(table);
      addRows(table, account);

      document.add(table);
      document.close();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
