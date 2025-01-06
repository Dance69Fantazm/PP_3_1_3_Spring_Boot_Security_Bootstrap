package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

@Component
public interface RoleService {
    List<Role> findAll();

    Optional<Role> findByIdRole(long id);

    void save(Role role);

    Role findByRoleName(String role);
}
