package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
/*        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleRepository.findById(role.getId()).get());
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);*/
    }

    @Override
    public User createUser(User user, Set<Role> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        for (Role role : roles) {
            Role newRole = role;
            if (newRole != null) {
                roleSet.add(newRole);
            }
        }
        user.setRoles(roleSet);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        var currentPassword = userRepository.findById(user.getId()).get().getPassword();
        if (!user.getPassword().equals(currentPassword)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
