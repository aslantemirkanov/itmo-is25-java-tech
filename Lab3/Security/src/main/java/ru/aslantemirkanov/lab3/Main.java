package ru.aslantemirkanov.lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ru.aslantemirkanov.lab3.dataaccess.repository")
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        //ApplicationContext context = SpringApplication.run(Main.class, args);
//        UserService userService = context.getBean(UserService.class);
//        User user = userService.findByUsername("aslan");
//        System.out.println(user.getPassword() + " " + user.getUsername());
    }
}