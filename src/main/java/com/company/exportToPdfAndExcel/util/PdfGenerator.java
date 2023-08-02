package com.company.exportToPdfAndExcel.util;


import com.company.exportToPdfAndExcel.dto.StudentDTO;
import com.company.exportToPdfAndExcel.service.AttachService;


import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;


@Component
public class PdfGenerator {

    @Value("${attach.upload.folder}")
    private String attachFolder;
    @Autowired
    private AttachService attachService;

    public ByteArrayInputStream createPdf2(StudentDTO studentDTO) throws FileNotFoundException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String path1 = "test3.pdf";
        PdfWriter pdfWriter = new PdfWriter(out);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        float twocol = 285f;
        float twocol150 = twocol + 150f;
        float twocolWidth[] = {twocol150, twocol};
        float fullwidth[] = {190f};

//        Paragraph paragraph=new Paragraph("Student Data");
//        document.add(paragraph);

        Table table = new Table(twocolWidth);
        table.addCell(new Cell().add("Student Image").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable = new Table(new float[]{twocol / 2, twocol / 2});
        nestedTable.addCell(new Cell().add("Name").setBold().setBorder(Border.NO_BORDER));
        nestedTable.addCell(new Cell().add("Alisher").setBorder(Border.NO_BORDER));
        nestedTable.addCell(new Cell().add("Surnaem").setBold().setBorder(Border.NO_BORDER));
        nestedTable.addCell(new Cell().add("Alisherov").setBorder(Border.NO_BORDER));

        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));
        document.add(table);

        Border border = new SolidBorder(Color.GRAY, 1f / 2f);
        Table divider = new Table(fullwidth);
        divider.setBorder(border);
        document.add(divider);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

//    public ByteArrayInputStream createPdf1(StudentDTO studentDTO) throws IOException, DocumentException {
//        Document document = new Document();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//
//        String path = attachService.get(studentDTO.getStudentPhotoID()).getPath();
//        String extension = attachService.get(studentDTO.getStudentPhotoID()).getExtension();
//        PdfWriter.getInstance(document,out);
//        document.open();
//        document.setPageSize(PageSize.A4);
//        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
//        Paragraph para = new Paragraph( "Student Data", font);
//        para.setAlignment(Element.ALIGN_CENTER);
//        document.add(para);
//        document.add(Chunk.NEWLINE);
//
//        Image image=Image.getInstance(attachFolder + path + "/" + studentDTO.getStudentPhotoID() + "." + extension);
//        image.setAbsolutePosition(70, 700);
//        image.scaleToFit(120, 180);
//        document.add(image);
//        document.add(Chunk.NEWLINE);
//        Paragraph para2 = new Paragraph( "More Information", font);
//        para2.setAlignment(Element.ALIGN_CENTER);
//        document.add(para2);
//        document.add(Chunk.NEWLINE);
//
//
////        document.add(new Phrase("Test Phrase1."));
////        document.add(new Phrase("Test Phrase2."));
////        Chapter chapter = new Chapter("Chapter 1", 1);
////        chapter.addSection("Section 1",2);
////        document.add(chapter);
//        document.close();
//        return new ByteArrayInputStream(out.toByteArray());
//    }

//    public ByteArrayOutputStream createPdfFromDto(StudentDTO studentDTO) throws IOException {
//
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage(PDRectangle.A4);
//        document.addPage(page);
//        String path = attachService.get(studentDTO.getStudentPhotoID()).getPath();
//        String extension = attachService.get(studentDTO.getStudentPhotoID()).getExtension();
//        // Create a new font object
//        PDFont font = PDType1Font.HELVETICA_BOLD;
//        PDImageXObject pdImage = PDImageXObject.createFromFileByContent(new File(attachFolder + path + "/" + studentDTO.getStudentPhotoID() + "." + extension), document);
//
//
//        // Create a new content stream
//        PDPageContentStream contentStream = new PDPageContentStream(document, page);
//        contentStream.drawImage(pdImage, 30, 70);
//        // Write the title
//        contentStream.beginText();
//        contentStream.setFont(font, 16);
//        contentStream.newLineAtOffset(50, 820);
//        contentStream.showText("Student");
//        contentStream.endText();
//
//        // Write the DTO data
//        contentStream.beginText();
//        contentStream.setFont(font, 12);
//        contentStream.newLineAtOffset(30, 800);
//        contentStream.showText("Full Name: " + studentDTO.getName() + " " + studentDTO.getSurname() + " " + studentDTO.getFamilyName());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Birth Place: " + studentDTO.getBirthPlace() + "     Birthday: " + studentDTO.getBirthday());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Contact: " + studentDTO.getPhone());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Passport data");
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Citizenship: " + studentDTO.getCitizenship() + "  Gender:" + studentDTO.getGender());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Seria: " + studentDTO.getPassportSerial() + "    Number:" + studentDTO.getPassportNumber() + "    PINFL:" + studentDTO.getPinfl());
//        contentStream.newLineAtOffset(0, -20);
//
//        contentStream.showText("Issue date: " + studentDTO.getIssueDate() + "    Expiry date:" + studentDTO.getExpiryDate());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Family status: " + studentDTO.getMarriageStatus() + "    Working company:" + studentDTO.getWorkplace());
//
//        contentStream.endText();
//        // Close the content stream
//        contentStream.close();
//
//        // Save the document to a byte array
//        document.save(baos);
//
//        // Close the document
//        document.close();
//
//        return baos;
//    }

//    private AttachService attachService;
//
////    public PdfGenerator(StudentDTO studentDTO, AttachService attachService) {
////        this.studentDTO = studentDTO;
////        this.attachService = attachService;
////    }
//
//    public PdfGenerator(StudentDTO studentDTO) {
//        this.studentDTO = studentDTO;
//    }
//
//    public void export(HttpServletResponse response) throws DocumentException, IOException {
//        Document document = new Document(PageSize.A4);
//        PdfWriter.getInstance(document, response.getOutputStream());
//
//        document.open();
//        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        font.setSize(18);
//        font.setColor(Color.BLUE);
//
//        Paragraph p = new Paragraph("About Student", font);
//        p.setAlignment(Paragraph.ALIGN_CENTER);
//
//
//
//        Font font2 = FontFactory.getFont(FontFactory.COURIER, 16);
//        Chunk chunk = new Chunk(studentDTO.getName()+" "+studentDTO.getSurname(), font2);
//
//        document.add(chunk);
//
//
//        document.add(p);
//
//
//        document.close();
//
//    }

}
