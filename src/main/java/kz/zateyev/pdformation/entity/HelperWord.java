package kz.zateyev.pdformation.entity;

import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelperWord {

    // Объект класса MyDataManager для работы с данными
    private MyDataManager dmg;
    // Данные из фала *.xslx
    private List<HashMap> clientsRows;

    // Инициирует создание файлов MS Word
    public void createWord() {
        // Создаем объект класса MyDataManager для работы с данными
        dmg = new MyDataManager();
        try {
            // Извлекаем данные из файла MS Excel
            clientsRows = dmg.getDataBlock();
            // <editor-fold defaultstate="collapsed" desc="Catch clauses">
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelperWord.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HelperWord.class.getName()).log(Level.SEVERE, null, ex);
            //</editor-fold>
        }
        // Создаем файл MS Word и заполняем его
        addDataBlock();

    }

    // Создает файл MS Word и заполняет его
    private void addDataBlock() {
        int num = 0;
        // Считываем информацию о каждом клменте
        for (HashMap row : clientsRows) {
            try {
                num++;
                // Извлекаем данные о существующем объекте MS Word
                WordprocessingMLPackage wordMLPackage =
                        WordprocessingMLPackage
                                .load(new File("C:\\Users\\Жасулан\\Desktop\\tmp\\template2.docx"));
                // Создаем объект для вставки значений в поля слияния
                List<Map<DataFieldName, String>> data =
                        new ArrayList<Map<DataFieldName, String>>();

                // Заполняем значения для полей слияния
                Map<DataFieldName, String> map =
                        new HashMap<DataFieldName, String>();
                map.put(new DataFieldName("Имя"), row.get("NAME").toString());
                map.put(new DataFieldName("Город"), row.get("CITY").toString());
                map.put(new DataFieldName("Адрес_1"), "улица Кунаева 8");
                data.add(map);
                // Создаем новый объект MS Word на основе существующего и
                // значений полей слияния
                WordprocessingMLPackage output =
                        MailMerger.getConsolidatedResultCrude(
                                wordMLPackage, data);
                // Сохраняем объект в файл
                output.save(new File("C:\\Users\\Жасулан\\Desktop\\tmp\\"
                        + num + ". " + row.get("NAME") + ".docx"));
                // <editor-fold defaultstate="collapsed" desc="Catch clauses">
            } catch (InvalidFormatException ex) {
                Logger.getLogger(HelperWord.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Docx4JException ex) {
                Logger.getLogger(HelperWord.class.getName()).log(Level.SEVERE, null, ex);
            }
            // </editor-fold>
        }
    }
}