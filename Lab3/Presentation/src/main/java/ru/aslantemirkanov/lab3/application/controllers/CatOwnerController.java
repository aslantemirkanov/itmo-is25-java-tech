package ru.aslantemirkanov.lab3.application.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.application.dto.CatOwnerDto;
import ru.aslantemirkanov.lab3.application.services.CatOwnerServiceImpl;
import ru.aslantemirkanov.lab3.application.services.UserService;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatColor;
import ru.aslantemirkanov.lab3.dataaccess.entities.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/owner")
@Validated
public class CatOwnerController {
    @Autowired
    CatOwnerServiceImpl catOwnerService;

    @Autowired
    UserService userService;

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

    @GetMapping("/getAllCats")
    public List<CatDto> getAllCats(@RequestParam("id") Long id, Principal principal) {
        if (!getPrincipal(id, principal)) return new ArrayList<>();
        return catOwnerService.getCatsById(id);
    }

    @GetMapping("/getAllCatsByColor")
    public List<CatDto> getAllCatsByColor(@RequestParam("id") Long id, @RequestParam("color") CatColor color,
                                          Principal principal) {
        if (getPrincipal(id, principal)) return new ArrayList<>();
        return catOwnerService.getCatsByColor(id, color);
    }


    @DeleteMapping("/delete")
    public void addCat(@RequestParam("id") Long id) {
        catOwnerService.deleteCatOwner(id);
    }
}
