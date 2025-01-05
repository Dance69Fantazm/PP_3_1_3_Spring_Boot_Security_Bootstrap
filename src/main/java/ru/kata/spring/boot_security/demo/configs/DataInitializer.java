package ru.kata.spring.boot_security.demo.configs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));
        Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleUser = new HashSet<>();
        roleAdmin.add(roleService.findByRoleName("ROLE_ADMIN"));
        roleUser.add(roleService.findByRoleName("ROLE_USER"));
        User admin = new User();
        admin.setName("admin");
        admin.setSurname("admin");
        admin.setRoles(roleAdmin);
        admin.setAge(33);
        admin.setEmail("admin@mail.ru");
        admin.setPassword("admin");
        User user = new User();
        user.setName("user");
        user.setSurname("user");
        user.setRoles(roleUser);
        user.setAge(15);
        user.setEmail("user@mail.ru");
        user.setPassword("user");
        userService.saveUser(user);
        userService.saveUser(admin);
    }
}
