package ru.aslantemirkanov.lab3.application.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.application.dto.CatOwnerDto;
import ru.aslantemirkanov.lab3.application.services.CatOwnerService;

import java.util.List;

@RestController
@RequestMapping("/owner")
@Validated
public class CatOwnerController {
    @Autowired
    CatOwnerService catOwnerService;

    @PostMapping("/register")
    public void ownerRegistration(@Valid @RequestBody CatOwnerDto catOwnerDto) {
        catOwnerService.createCatOwner(catOwnerDto);
    }

    @GetMapping("/addCat")
    public void addCat(@RequestParam("idOwner") Long idFirst, @RequestParam("idCat") Long idSecond) {
        catOwnerService.addOwner(idFirst, idSecond);
    }

    @GetMapping("/getAllOwners")
    public List<CatOwnerDto> getAllOwners() {
        return catOwnerService.findAll();
    }

    @GetMapping("/getOwnerById")
    public CatOwnerDto getOwnerById(@RequestParam("id") Long id) {
        return catOwnerService.findById(id);
    }

    @GetMapping("/getAllCats")
    public List<CatDto> getAllCats(@RequestParam("id") Long id) {
        return catOwnerService.getCatsById(id);
    }

    @DeleteMapping("/delete")
    public void addCat(@RequestParam("id") Long id) {
        catOwnerService.deleteCatOwner(id);
    }
}
