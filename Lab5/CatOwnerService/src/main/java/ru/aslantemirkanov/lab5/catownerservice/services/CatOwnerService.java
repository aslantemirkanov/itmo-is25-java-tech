package ru.aslantemirkanov.lab5.catownerservice.services;

import ru.aslantemirkanov.lab5.dataservice.dto.CatDto;
import ru.aslantemirkanov.lab5.dataservice.dto.CatOwnerDto;
import ru.aslantemirkanov.lab5.dataservice.entities.CatColor;

import java.util.List;

public interface CatOwnerService {
    List<CatOwnerDto> findAll(String message);
    CatOwnerDto findById(long id);
    void createCatOwner(CatOwnerDto catOwnerDto);
    void addOwner(List<Long> info);
    void deleteCatOwner(long id);
}

