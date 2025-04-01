package ua.nmu.smahin.spring.services;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nmu.smahin.spring.models.ItemModel;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    RepositoryService repository;
    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        cell.setCellValue(value.toString());
        cell.setCellStyle(style);
    }
    private void setFirstRow(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("Товари");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        createCell(sheet, row, 0, "ID", style);
        createCell(sheet, row, 1, "Посилання на картинку", style);
        createCell(sheet, row, 2, "Назва", style);
        createCell(sheet, row, 3, "Ціна UAH", style);
        createCell(sheet, row, 4, "Ціна USD", style);
    }

    public void getReport(HttpServletResponse response) throws IOException {
        List<ItemModel> itemList = repository.get();
        XSSFWorkbook workbook = new XSSFWorkbook();
        setFirstRow(workbook);

        XSSFSheet sheet = workbook.getSheet("Товари");
        CellStyle style = workbook.createCellStyle();

        for (int i = 1; i<=itemList.size()-1; i++) {
            Row row = sheet.createRow(i);
            int column = 0;

            createCell(sheet, row, column, itemList.get(i).getId(), style);
            column++;
            createCell(sheet, row, column, itemList.get(i).getImgURL(), style);
            column++;
            createCell(sheet, row, column, itemList.get(i).getName(), style);
            column++;
            createCell(sheet, row, column, itemList.get(i).getPriceUAH(), style);
            column++;
            createCell(sheet, row, column, itemList.get(i).getPriceUSD(), style);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

}
