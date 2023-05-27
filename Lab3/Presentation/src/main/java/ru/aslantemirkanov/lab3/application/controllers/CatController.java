package ru.aslantemirkanov.lab3.application.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.application.mapping.CatDtoMapping;
import ru.aslantemirkanov.lab3.application.services.CatServiceImpl;
import ru.aslantemirkanov.lab3.application.services.UserService;
import ru.aslantemirkanov.lab3.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatColor;
import ru.aslantemirkanov.lab3.dataaccess.entities.User;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cat")
@Validated
public class CatController {
    @Autowired
    CatServiceImpl catService;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public void catRegistration(@Valid @RequestBody CatDto catDto) {
        catService.addCat(catDto);
    }

    @PostMapping("/registerFriend")
    public void friendRegistration(@RequestParam("id1") Long idFirst, @RequestParam("id2") Long idSecond) {
        catService.addFriend(idFirst, idSecond);
    }

    @GetMapping("/getCatById")
    public CatDto getCatById(@RequestParam("id") Long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(principal.getName());
        Cat cat = catService.getCatById(id);
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
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(principal.getName());
        List<CatDto> catDtoList = catService.getAllCats();

        return catService.getAllCats();
    }

    @GetMapping("/getCatsByColor")
    public List<CatDto> getCatsByColor(@RequestParam("color") CatColor color) {
        return catService.getCatsDtoByColor(color);
    }

    @PatchMapping("/changeName")
    public void changeNameById(@RequestParam("id") Long id, @RequestParam("name") String name) {
        catService.changeNameById(id, name);
    }

    @PatchMapping("/changeBreed")
    public void changeBreedById(@RequestParam("id") Long id, @RequestParam("breed") String breed) {
        catService.changeBreedById(id, breed);
    }

    @PatchMapping("/changeColor")
    public void changeColorById(@RequestParam("id") Long id, @RequestParam("color") CatColor color) {
        catService.changeColorById(id, color);
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam("id") Long id) {
        catService.delete(id);
    }

    private boolean getPrincipal(Long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(principal.getName());
        if (user.getCatOwner() == null){
            return true;
        }
        if (!Objects.equals(id, user.getCatOwner().getId())){
            return true;
        }
        return false;
    }
}
