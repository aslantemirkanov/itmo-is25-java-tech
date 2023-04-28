package ru.aslantemirkanov.lab3.application.services;


import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatColor;

import java.util.List;

public interface CatService {
    CatDto getCatDtoById(long id);
    List<CatDto> getAllFriends(long id);
    List<CatDto> getAllCats();
    List<CatDto> getCatsDtoByColor(CatColor color);
    void addCat(CatDto catDto);
    void updateCat(CatDto catDto);
    void delete(long id);
    void addFriend(long idFirst, long idSecond);
    Cat getCatById(long id);
}
