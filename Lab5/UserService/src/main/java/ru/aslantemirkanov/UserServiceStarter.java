package ru.aslantemirkanov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories("ru.aslantemirkanov.lab3.dataaccess.repository")
@SpringBootApplication
public class UserServiceStarter {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceStarter.class, args);
    }
}