package ru.aslantemirkanov.lab2.presentation.controllers;

import ru.aslantemirkanov.lab2.application.dto.CatColorDto;
import ru.aslantemirkanov.lab2.application.dto.CatDto;
import ru.aslantemirkanov.lab2.application.services.CatService;

import java.time.LocalDate;
import java.util.List;

public class CatController {

    private CatService catService;

    public CatController() {
        catService = new CatService();
    }

    public CatDto createCat(String name, String breed, CatColorDto color, LocalDate birthDate) {
        return catService.createCatDto(name, breed, color, birthDate);
    }

    public CatDto findById(long id){
        return catService.findCatById(id);
    }

    public void addFriend(long idFirst, long idSecond) {
        catService.addFriend(idFirst, idSecond);
    }

    public void deleteCat(long id){
        catService.deleteCat(id);
    }

    public List<CatDto> getAllFriends(long id) {
        return catService.getAllFriends(id);
    }
}
