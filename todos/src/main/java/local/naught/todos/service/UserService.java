package local.naught.todos.service;

import local.naught.todos.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findUserById(long id);

    User findUserByUsername(String username);

    void delete(long id);

    User save(User user);

    User update(User user, long id);
}