package local.naught.todos;

import local.naught.todos.model.Role;
import local.naught.todos.model.Todo;
import local.naught.todos.model.User;
import local.naught.todos.model.UserRoles;
import local.naught.todos.repos.RoleRepository;
import local.naught.todos.repos.TodoRepository;
import local.naught.todos.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    RoleRepository rolerepos;
    UserRepository userrepos;
    TodoRepository todorepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos, TodoRepository todorepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
        this.todorepos = todorepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));

        ArrayList<Todo> todos = new ArrayList<>();

        rolerepos.save(r1);
        rolerepos.save(r2);

        User u1 = new User("barnbarn", "ILuvM4th!", admins);
        User u2 = new User("admin", "password", admins);

        Todo t1 = new Todo("Finish java-orders-swagger", u2);
        Todo t2 = new Todo("Feed the turtles", u2);
        Todo t3 = new Todo("Complete the sprint challenge", u2);
        Todo t4 = new Todo("Walk the dogs", u1);
        Todo t5 = new Todo("provide feedback to my instructor", u1);

        u2.getTodos().add(t1);
        u2.getTodos().add(t2);
        u2.getTodos().add(t3);

        u1.getTodos().add(t4);
        u1.getTodos().add(t5);

        todorepos.save(t1);
        todorepos.save(t2);
        todorepos.save(t3);
        todorepos.save(t4);
        todorepos.save(t5);

        // the date and time string should get coverted to a datetime Java data type. This is done in the constructor

        userrepos.save(u1);
        userrepos.save(u2);
    }
}
