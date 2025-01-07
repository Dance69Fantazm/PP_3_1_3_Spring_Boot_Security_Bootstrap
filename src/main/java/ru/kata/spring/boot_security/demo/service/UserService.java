package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;

@Component
public interface UserService {

    List<User> findAllUsers();

    void saveUser(User user, Set<Role> roles);

    void updateUser(User user, Set<Role> roles);

    void deleteUser(Long id);

}

