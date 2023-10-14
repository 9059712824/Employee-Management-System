package com.learning.employemanagementsystem.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@Service
public class ExcelService {
    public void readFile(MultipartFile file, String sheetNumber) throws IOException {
        int numberOfSheet = Integer.parseInt(sheetNumber);

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        if (numberOfSheet == 0) {
            System.out.println("Incorrect sheet");
        }
        for (int i = 0; i < numberOfSheet; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if(sheet == null){
                System.out.println("Current sheet is null");
            }
            System.out.println(sheet.getSheetName());

            DataFormatter dataFormatter = new DataFormatter();

            Iterator<Row> rowIterator = sheet.rowIterator();
            Row rowHeader = sheet.getRow(0);


            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.println(cellValue + "\t");
                }
                System.out.println();
            }
        }
    }
}
