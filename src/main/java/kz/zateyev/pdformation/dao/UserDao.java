package kz.zateyev.pdformation.dao;

import kz.zateyev.pdformation.entity.User;

public interface UserDao {
    User findById(Long l);

    User findByEmail(String email);

    void update(User user);

    User save(User user);

    User merge(User user);

    User insert(User user);

    boolean remove(User user);

    boolean removeById(long id);
}
