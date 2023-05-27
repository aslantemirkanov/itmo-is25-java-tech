package ru.aslantemirkanov.lab5.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aslantemirkanov.lab5.dataservice.entities.User;
import ru.aslantemirkanov.lab5.dataservice.exceptions.user.ExtraOwnerException;
import ru.aslantemirkanov.lab5.userservice.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public void friendRegistration(@RequestParam("username") String username, @RequestParam("password") String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userService.addUser(user);
    }

    @GetMapping("/addOwner")
    public void addOwnerToUser(@RequestParam("id")Long id, Principal principal){
        User user = userService.findByUsername(principal.getName());
        if (user.getCatOwner() != null){
            throw new ExtraOwnerException(user.getUsername());
        }
        userService.addCatOwner(user, id);
    }
}
