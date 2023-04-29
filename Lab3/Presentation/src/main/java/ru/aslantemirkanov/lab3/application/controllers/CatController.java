package ru.aslantemirkanov.lab3.application.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.application.services.CatServiceImpl;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatColor;

import java.util.List;

@RestController
@RequestMapping("/cat")
@Validated
public class CatController {
    @Autowired
    private CatServiceImpl catService;

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
        return catService.getCatDtoById(id);
    }

    @GetMapping("/getAllCats")
    public List<CatDto> getAllCats() {
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
}
