package com.company.exportToPdfAndExcel.util;
import java.io.IOException;
import java.util.List;


import com.company.exportToPdfAndExcel.entity.StudentEntity;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class StudentExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<StudentEntity> studentEntityList;

    public StudentExcelExporter(List<StudentEntity> studentEntityList) {
        this.studentEntityList = studentEntityList;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Students");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "name", style);
        createCell(row, 2, "surname", style);
        createCell(row, 3, "family name", style);
        createCell(row, 4, "phone", style);
        createCell(row, 5, "birthday", style);
        createCell(row, 6, "gender", style);
        createCell(row, 7, "citizenship", style);
        createCell(row, 8, "passportSerial", style);
        createCell(row, 9, "passportNumber", style);
        createCell(row, 10, "pinfl", style);
        createCell(row, 11, "issueDate", style);
        createCell(row, 12, "expiryDate", style);
        createCell(row, 13, "workplace", style);
        createCell(row, 14, "marriageStatus", style);
        createCell(row, 15, "birthPlace", style);
        createCell(row, 16, "studentPhotoID", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (StudentEntity studentEntity : studentEntityList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, studentEntity.getId(), style);
            createCell(row, columnCount++, studentEntity.getName(), style);
            createCell(row, columnCount++, studentEntity.getSurname(), style);
            createCell(row, columnCount++, studentEntity.getFamilyName(), style);
            createCell(row, columnCount++, studentEntity.getPhone(), style);
            createCell(row, columnCount++, studentEntity.getBirthday().toString(), style);
            createCell(row, columnCount++, studentEntity.getGender().toString(), style);
            createCell(row, columnCount++, studentEntity.getCitizenship(), style);
            createCell(row, columnCount++, studentEntity.getPassportSerial(), style);
            createCell(row, columnCount++, studentEntity.getPassportNumber(), style);
            createCell(row, columnCount++, studentEntity.getPinfl(), style);
            createCell(row, columnCount++, studentEntity.getIssueDate().toString(), style);
            createCell(row, columnCount++, studentEntity.getExpiryDate().toString(), style);
            createCell(row, columnCount++, studentEntity.getWorkplace(), style);
            createCell(row, columnCount++, studentEntity.getMarriageStatus().toString(), style);
            createCell(row, columnCount++, studentEntity.getBirthPlace(), style);
            createCell(row, columnCount++, studentEntity.getAttachEntity().getId(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
