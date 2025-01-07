package ru.kata.spring.boot_security.demo.configs;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    private final UserDao userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserDao userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeUser() {
        Role roleAdmin = roleService.findByRoleName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role("ROLE_ADMIN");
            roleService.save(roleAdmin);
        }

        Role roleUser = roleService.findByRoleName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role("ROLE_USER");
            roleService.save(roleUser);
        }

        if (!userRepository.existsByEmail("admin@mail.ru")) {
            User admin = new User("admin", "admin", 18, "admin@mail.ru",
                    passwordEncoder.encode("admin"));
            Set<Role> setAdmin = new HashSet<>();
            setAdmin.add(roleAdmin);
            admin.setRoles(setAdmin);
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("user@mail.ru")) {
            User user = new User("user", "user", 18, "user@mail.ru",
                    passwordEncoder.encode("user"));
            Set<Role> setUser = new HashSet<>();
            setUser.add(roleUser);
            user.setRoles(setUser);
            userRepository.save(user);
        }
    }
}


