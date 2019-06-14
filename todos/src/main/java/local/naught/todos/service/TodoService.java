package local.naught.todos.service;

import local.naught.todos.model.Todo;

import java.util.List;

public interface TodoService {
    Todo save(Todo todo);
    List<Todo> findAll();
    Todo findByTodoid(long id);
    Todo update(Todo todo, long id);
}
