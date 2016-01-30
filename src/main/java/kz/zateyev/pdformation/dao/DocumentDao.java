package kz.zateyev.pdformation.dao;

import kz.zateyev.pdformation.entity.Document;

import javax.print.Doc;

public interface DocumentDao {
    Document findById(Long id);
    Document findByName(String name);
    Document insert(Document document);
    boolean remove(Document document);
    boolean removeById(Long id);
}
