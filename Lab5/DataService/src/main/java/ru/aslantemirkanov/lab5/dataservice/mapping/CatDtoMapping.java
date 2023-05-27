package ru.aslantemirkanov.lab5.dataservice.mapping;


import ru.aslantemirkanov.lab5.dataservice.dto.CatDto;
import ru.aslantemirkanov.lab5.dataservice.entities.Cat;

public class CatDtoMapping {
    public static CatDto asCatDto(Cat cat) {
        return new CatDto(cat.getName(),
                cat.getBreed(),
                cat.getColor(),
                cat.getBirthDate(),
                cat.getId());
    }

    public static Cat asCat(CatDto catDto) {
        return new Cat(catDto.getName(),
                catDto.getBreed(),
                catDto.getColor(),
                catDto.getBirthDate());
    }
}
