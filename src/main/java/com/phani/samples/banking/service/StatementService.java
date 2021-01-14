package com.phani.samples.banking.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
public class StatementService {

  public ByteArrayInputStream generateTablePdf() throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Document document = new Document();
    PdfWriter.getInstance(document,out);

    document.open();

    PdfPTable table = new PdfPTable(3);
    addTableHeader(table);
    addRows(table);

    document.add(table);
    document.close();
    return new ByteArrayInputStream(out.toByteArray());
  }

  private void addTableHeader(PdfPTable table) {
    Stream.of("column header 1", "column header 2", "column header 3")
            .forEach(columnTitle -> {
              PdfPCell header = new PdfPCell();
              header.setBackgroundColor(BaseColor.LIGHT_GRAY);
              header.setBorderWidth(2);
              header.setPhrase(new Phrase(columnTitle));
              table.addCell(header);
            });
  }


  private void addRows(PdfPTable table) {
    table.addCell("row 1, col 1");
    table.addCell("row 1, col 2");
    table.addCell("row 1, col 3");
  }

}
