package local.naught.todos.controller;

import local.naught.todos.model.Todo;
import local.naught.todos.model.User;
import local.naught.todos.repos.UserRepository;
import local.naught.todos.service.TodoService;
import local.naught.todos.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @GetMapping(value="/")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }



    @GetMapping(value="/mine")
    public ResponseEntity<?> findMine() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
        } else {
            String username = principal.toString();
            return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value="/{userid}")
    public ResponseEntity<?> findById(@PathVariable long userid) {
        return new ResponseEntity<>(userService.findUserById(userid), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value="/", consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> newUser(HttpServletRequest request, @Valid @RequestBody User newUser) throws URISyntaxException
    {
        newUser = userService.save(newUser);

        // set the location header for the newly created resource
//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI newUserURI = ServletUriComponentsBuilder.fromUriString(request.getServerName() + ":" + request.getLocalPort() + "/users/{userid}").buildAndExpand(newUser.getUserid()).toUri();
//        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/userid/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable long userid) {
        userService.delete(userid);
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value="/todo/{userid}")
    public ResponseEntity<?> newTodoonUser(@PathVariable long userid, @Valid @RequestBody Todo newTodo) {
        User targetUser = userService.findUserById(userid);

        List<Todo> newTodos = new ArrayList<>();
        newTodos.addAll(targetUser.getTodos());
        newTodos.add(newTodo);
        targetUser.setTodos(newTodos);

        newTodo.setUserid(userid);

        userService.update(targetUser, userid);
        todoService.save(newTodo);
        return new ResponseEntity<>(targetUser, HttpStatus.CREATED);
//        URI newTodoURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{todoid}").buildAndExpand(newTodo.getTodoid()).toUri();
    }
}
