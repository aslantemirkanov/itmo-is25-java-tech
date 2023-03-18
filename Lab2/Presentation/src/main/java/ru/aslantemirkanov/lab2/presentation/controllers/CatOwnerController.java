package ru.aslantemirkanov.lab2.presentation.controllers;

import ru.aslantemirkanov.lab2.application.dto.CatDto;
import ru.aslantemirkanov.lab2.application.dto.CatOwnerDto;
import ru.aslantemirkanov.lab2.application.services.CatOwnerService;

import java.time.LocalDate;
import java.util.List;

public class CatOwnerController {

    private CatOwnerService catOwnerService;

    public CatOwnerController() {
        catOwnerService = new CatOwnerService();
    }

    public CatOwnerDto createCatOwnerDto(String name, LocalDate birthDate) {
        return catOwnerService.createCatOwnerDto(name, birthDate);
    }

    public void addCatForOwner(CatDto catDto, CatOwnerDto catOwnerDto) {
        catOwnerService.addOwner(catDto, catOwnerDto);
    }

    public void addCatForOwnerById(long catId, long catOwnerId) {
        catOwnerService.addOwnerById(catId, catOwnerId);
    }

    public List<CatDto> getCatsById(long id){
        return catOwnerService.getCatsById(id);
    }
}
