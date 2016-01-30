package kz.zateyev.pdformation.dao;

import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.User;

import java.util.List;

public interface PackDao {
    Pack findById(Long id);
    Pack findByName(String name);
    List<Pack> findByUser(User user);
    void update(Pack pack);
    Pack save(Pack pack);
    Pack insert(Pack pack);
    boolean remove(Pack pack);
    boolean removeById(Long id);
}
