package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validation.PersonValidator;

import javax.validation.Valid;

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

    @GetMapping("/users")
    public String showUsersTable(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute @Valid User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin/users";
    }
}
