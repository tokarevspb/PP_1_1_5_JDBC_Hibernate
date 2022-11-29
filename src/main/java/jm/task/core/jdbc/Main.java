package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.*;
import java.util.function.*;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Sergei", "Tokarev", (byte) 21);
        userService.saveUser("Alex", "Alexeev", (byte) 44);
        userService.saveUser("Ivan", "Ivanov", (byte) 55);
        userService.saveUser("Artyom", "Artyomov", (byte) 11);
        var users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
