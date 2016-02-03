package kz.zateyev.pdformation.dao;

import kz.zateyev.pdformation.entity.Document;
import kz.zateyev.pdformation.entity.Pack;

import javax.print.Doc;
import java.util.List;

public interface DocumentDao {
    Document findById(Long id);
    Document findByName(String name);
    List<Document> findByPack(Pack pack);
    Document insert(Document document);
    boolean remove(Document document);
    boolean removeById(Long id);
}
