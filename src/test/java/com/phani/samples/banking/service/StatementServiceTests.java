package com.phani.samples.banking.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootTest
public class StatementServiceTests {

  @Autowired
  StatementService statementService;

  @Test
  public void testGenerateTablePdf() throws Exception{
    File targetFile = new File("sample_table.pdf");
    ByteArrayInputStream byteArrayInputStream = generateTablePdf();
    OutputStream outStream = new FileOutputStream(targetFile);
    outStream.write(byteArrayInputStream.readAllBytes());
  }

  @Test
  public void testGenerateHelloWorldPdf() throws Exception{
    File targetFile = new File("sample.pdf");
    ByteArrayInputStream byteArrayInputStream = generateHelloWorldPdf();
    OutputStream outStream = new FileOutputStream(targetFile);
    outStream.write(byteArrayInputStream.readAllBytes());
  }

  public ByteArrayInputStream generateHelloWorldPdf() throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Document document = new Document();
    PdfWriter.getInstance(document, out);

    document.open();
    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    Chunk chunk = new Chunk("Hello World", font);

    document.add(chunk);
    document.close();
    return new ByteArrayInputStream(out.toByteArray());
  }

  public ByteArrayInputStream generateTablePdf() throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Document document = new Document();
    PdfWriter.getInstance(document,out);

    document.open();

    PdfPTable table = new PdfPTable(3);
    addTableHeader(table);
    addRows(table);
   // addCustomRows(table);

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

  private void addCustomRows(PdfPTable table)
          throws URISyntaxException, BadElementException, IOException {
  /*  Path path = Paths.get(ClassLoader.getSystemResource("eltropy.png").toURI());
    Image img = Image.getInstance(path.toAbsolutePath().toString());
    img.scalePercent(1);

    PdfPCell imageCell = new PdfPCell(img);
    table.addCell(imageCell);
*/
    PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 1"));
    //   horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(horizontalAlignCell);

    horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
 //   horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(horizontalAlignCell);

    PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
 //   verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    table.addCell(verticalAlignCell);
  }

}
