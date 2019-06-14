package local.naught.todos.repos;

import local.naught.todos.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    Todo findByTodoid(long id);
}
