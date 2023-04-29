package ru.aslantemirkanov.lab3.application.services;

import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.application.dto.CatOwnerDto;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatColor;

import java.util.List;

public interface CatOwnerService {
    List<CatOwnerDto> findAll();
    CatOwnerDto findById(long id);
    void createCatOwner(CatOwnerDto catOwnerDto);
    void addOwner(long catOwnerId, long catId);
    List<CatDto> getCatsById(long id);
    List<CatDto> getCatsByColor(long id, CatColor color);
    void deleteCatOwner(long id);
}

