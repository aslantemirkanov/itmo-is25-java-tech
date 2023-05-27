package ru.aslantemirkanov.lab3.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aslantemirkanov.lab3.application.exception.user.ExtraOwnerException;
import ru.aslantemirkanov.lab3.application.services.CatOwnerServiceImpl;
import ru.aslantemirkanov.lab3.application.services.UserService;
import ru.aslantemirkanov.lab3.dataaccess.entities.User;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CatOwnerServiceImpl catOwnerService;

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
