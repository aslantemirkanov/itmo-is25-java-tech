package ru.aslantemirkanov.lab5.userservice.controllers;

import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aslantemirkanov.lab5.dataservice.dto.CatOwnerDto;
import ru.aslantemirkanov.lab5.dataservice.entities.User;
import ru.aslantemirkanov.lab5.userservice.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/owner")
@Validated
public class CatOwnerController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    UserService userService;

    @GetMapping("/getAllOwners")
    public List<CatOwnerDto> getAllOwners() {
        String message = "getOwners";
        return (List<CatOwnerDto>) rabbitTemplate.convertSendAndReceive("getAllOwnersQueue", message);
    }
    @PostMapping("/register")
    public void ownerRegistration(@Valid @RequestBody CatOwnerDto catOwnerDto) {
        rabbitTemplate.convertAndSend("registerOwnerQueue", catOwnerDto);
    }
    @GetMapping("/addCat")
    public void addCat(@RequestParam("idOwner") Long idFirst, @RequestParam("idCat") Long idSecond) {
        List<Long> info = new ArrayList<>();
        info.add(idFirst);
        info.add(idSecond);
        rabbitTemplate.convertAndSend("addOwnerForCatQueue", info);
    }

    @GetMapping("/getOwnerById")
    public CatOwnerDto getOwnerById(@RequestParam("id") Long id) {
        return (CatOwnerDto) rabbitTemplate.convertSendAndReceive("getOwnerByIdQueue", id);
    }

    private boolean getPrincipal(Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user.getCatOwner() == null) {
            return false;
        }
        if (!Objects.equals(id, user.getCatOwner())) {
            return true;
        }
        return false;
    }
}
