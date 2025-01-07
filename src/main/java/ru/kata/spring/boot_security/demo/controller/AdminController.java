package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validation.PersonValidator;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PersonValidator personValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PersonValidator personValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String showUsersTable(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute User user, @RequestParam("role") Set<Role> roles) {
        userService.saveUser(userService.createUser(user, roles));
        return "redirect:/admin";
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("user") User user, @RequestParam("id") long id, @RequestParam(value = "role", required = false) Set<Role> roles) {
        userService.update(id, userService.updateUser(user, roles, id));
        return "redirect:/admin/";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin";
    }
}
