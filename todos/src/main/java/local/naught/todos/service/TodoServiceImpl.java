package local.naught.todos.service;

import local.naught.todos.model.Todo;
import local.naught.todos.model.User;
import local.naught.todos.repos.TodoRepository;
import local.naught.todos.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value="todoservice")
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepository todorepos;

    @Autowired
    UserRepository userrepos;

    @Transactional
    @Override
    public Todo save(Todo todo) {
        return todorepos.save(todo);
    }

    @Override
    public List<Todo> findAll() {
        List<Todo> list = new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Todo update(Todo newTodo, long id) {
        return todorepos.save(newTodo);
    }

    @Transactional
    @Override
    public Todo findByTodoid(long id) {
        Todo target = todorepos.findByTodoid(id);
        return target;
    }
}
