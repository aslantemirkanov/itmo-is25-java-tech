package ru.aslantemirkanov.lab5.dataservice.mapping;


import ru.aslantemirkanov.lab5.dataservice.dto.CatDto;
import ru.aslantemirkanov.lab5.dataservice.dto.CatOwnerDto;
import ru.aslantemirkanov.lab5.dataservice.entities.Cat;
import ru.aslantemirkanov.lab5.dataservice.entities.CatOwner;

import java.util.ArrayList;
import java.util.List;

public class CatOwnerMapping {
    public static CatOwnerDto asCatOwnerDto(CatOwner catOwner) {
        List<CatDto> catDtoList = new ArrayList<>();
        /*if (catOwner.getCats() != null) {
            for (int i = 0; i < catOwner.getCats().size(); ++i) {
                catDtoList.add(CatDtoMapping.asCatDto(catOwner.getCats().get(i)));
            }
        }*/
        return new CatOwnerDto(
                catOwner.getName(),
                catOwner.getBirthDate(),
                catDtoList
        );
    }

    public static CatOwner asCatOwner(CatOwnerDto catOwnerDto) {
        List<Cat> catList = new ArrayList<>();
/*        if (catOwnerDto.getCats() != null) {
            for (int i = 0; i < catOwnerDto.getCats().size(); ++i) {
                catList.add(CatDtoMapping.asCat(catOwnerDto.getCats().get(i)));
            }
        }*/
        return new CatOwner(catOwnerDto.getName(),
                catOwnerDto.getBirthDate(),
                catList);
    }
}
