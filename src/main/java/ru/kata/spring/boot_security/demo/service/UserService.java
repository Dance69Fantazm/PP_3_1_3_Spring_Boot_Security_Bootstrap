package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Component
public interface UserService {

    List<User> findAllUsers();

    void saveUser(User user);

    User createUser(User user, Set<Role> roles);

    void updateUser(User user);

    void deleteUser(Long id);
}