package kz.zateyev.pdformation.entity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MyDataManager {

    //Получаем данные из MS Excel
    public List<HashMap> getDataBlock() throws IOException {
        ArrayList<HashMap> res = new ArrayList<HashMap>();
        FileInputStream file = new FileInputStream(new File("C:\\Users\\Жасулан\\Desktop\\tmp\\clients.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        // Пропускаю первую строку. В моем случае в ней только заколовки
        if(rowIterator.hasNext()) rowIterator.next();
        //Пробегаемся по всем строкам
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            HashMap line = new HashMap();
            // В моей структуре файла мне интересны только 1-ая и 3-я строки
            Cell cell = row.getCell(0);
            line.put("NAME", cell.getStringCellValue());
            cell = row.getCell(2);
            line.put("CITY", cell.getStringCellValue());
            res.add(line);
        }
        file.close();
        return res;
    }
}