package ru.ssemenov.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import ru.ssemenov.entities.CustomsDeclaration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExcelFileWriter {

    public final String FONT_NAME = "Arial";
    public final short FONT_SIZE = 14;

    public File createExcelFile(List<CustomsDeclaration> declarations) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Декларации");
        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 8000);
        sheet.setColumnWidth(6, 10000);

        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setWrapText(true);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName(FONT_NAME);
        font.setFontHeightInPoints(FONT_SIZE);
        font.setBold(false);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Номер");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Грузоотправитель");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Статус");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Данные по инвойсу");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Фактурная стоимость груза");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Дата и время подачи ДТ");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Дата и время выпуска / отказа");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        for (int i = 0; i < declarations.size(); i++) {
            Row row = sheet.createRow(i + 1);

            Cell cell = row.createCell(0);
            cell.setCellValue(declarations.get(i).getNumber());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(declarations.get(i).getConsignor());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(CustomsDeclarationStatusEnum.valueOf(declarations.get(i).getStatus()).rusName);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(declarations.get(i).getInvoiceData());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(declarations.get(i).getGoodsValue().doubleValue());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(declarations.get(i).getDateOfSubmission().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(declarations.get(i).getDateOfRelease().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            cell.setCellStyle(style);
        }

        File file = new File("temp.xlsx");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file.getPath());
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
