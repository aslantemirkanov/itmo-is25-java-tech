package ru.aslantemirkanov.lab3.application.mapping;

import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.dataaccess.entities.Cat;

public class CatDtoMapping {
    public static CatDto asCatDto(Cat cat) {
        return new CatDto(cat.getName(),
                cat.getBreed(),
                cat.getColor(),
                cat.getBirthDate(),
                cat.getId());
    }

    public static Cat asCat(CatDto catDto){
        return new Cat(catDto.getName(),
                catDto.getBreed(),
                catDto.getColor(),
                catDto.getBirthDate());
    }
}
