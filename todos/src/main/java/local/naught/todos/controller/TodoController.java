package local.naught.todos.controller;

import local.naught.todos.model.Todo;
import local.naught.todos.repos.TodoRepository;
import local.naught.todos.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(value="/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(value="/todos")
    public ResponseEntity<?> findAllTodos() {
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }

    @PutMapping(value="/todos/{id}")
    public ResponseEntity<?> editTodo(@PathVariable long id, @RequestBody Todo newTodo) {
        Todo target = todoService.findByTodoid(id);
        System.out.println();
        target.setDescription(newTodo.getDescription());
        target.setCompleted(newTodo.getCompleted());
        target.setUserid(newTodo.getUserid());
        todoService.save(target);
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }
}
