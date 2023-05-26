package ru.aslantemirkanov.lab5.userservice.controllers;

import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aslantemirkanov.lab5.dataservice.dto.CatColorInfo;
import ru.aslantemirkanov.lab5.dataservice.dto.CatDto;
import ru.aslantemirkanov.lab5.dataservice.entities.Cat;
import ru.aslantemirkanov.lab5.dataservice.entities.CatColor;
import ru.aslantemirkanov.lab5.dataservice.entities.User;
import ru.aslantemirkanov.lab5.dataservice.exceptions.cat.NoneExistCatException;
import ru.aslantemirkanov.lab5.dataservice.mapping.CatDtoMapping;
import ru.aslantemirkanov.lab5.dataservice.repositories.CatRepository;
import ru.aslantemirkanov.lab5.userservice.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cat")
@Validated
public class CatController {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public void catRegistration(@Valid @RequestBody CatDto catDto) {
        System.out.println(catDto.toString());
        rabbitTemplate.convertAndSend("catRegisterQueue", catDto);
    }


    @PostMapping("/registerFriend")
    public void friendRegistration(@RequestParam("id1") Long idFirst, @RequestParam("id2") Long idSecond) {
        List<Long> message = new ArrayList<>();
        message.add(idFirst);
        message.add(idSecond);
        rabbitTemplate.convertAndSend("catFriendRegisterQueue", message);
        //catService.addFriend(idFirst, idSecond);
    }

    @Autowired
    CatRepository catRepository;
    @GetMapping("/getCatById")
    public CatDto getCatById(@RequestParam("id") Long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(principal.getName());
        Cat cat = catRepository.findById(id).orElseThrow(()
                -> new NoneExistCatException(id));
        if (cat.getCatOwner() == null){
            return null;
        }
        if (cat.getCatOwner().getId() == user.getId()){
            return CatDtoMapping.asCatDto(cat);
        }
        return null;
    }


    @GetMapping("/getAllCats")
    public List<CatDto> getAllCats() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(((UserDetails)principal).getUsername());
        return (List<CatDto>) rabbitTemplate.convertSendAndReceive("getAllCatsQueue", user.getId());
    }


    @GetMapping("/getCatsByColor")
    public List<CatDto> getCatsByColor(@RequestParam("color") CatColor color) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(((UserDetails)principal).getUsername());
        CatColorInfo catColorInfo = new CatColorInfo(color, user.getId());
        List<CatDto> catDtos =
                (List<CatDto>) rabbitTemplate.convertSendAndReceive("getCatsByColorQueue", catColorInfo);
        System.out.println(user.getUsername());
        System.out.println(catDtos);
        return catDtos;
    }
    @DeleteMapping("/delete")
    public void deleteById(@RequestParam("id") Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(((UserDetails)principal).getUsername());
        //catService.delete(id);
    }

}
