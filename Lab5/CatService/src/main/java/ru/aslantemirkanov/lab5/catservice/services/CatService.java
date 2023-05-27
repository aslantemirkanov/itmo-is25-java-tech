package ru.aslantemirkanov.lab5.catservice.services;

import ru.aslantemirkanov.lab5.dataservice.dto.CatColorInfo;
import ru.aslantemirkanov.lab5.dataservice.dto.CatDto;
import ru.aslantemirkanov.lab5.dataservice.entities.Cat;

import java.util.List;

public interface CatService {
    CatDto getCatDtoById(long id);
    List<CatDto> getAllFriends(long id);
    List<CatDto> getAllCats(Long id);
    List<CatDto> getCatsDtoByColor(CatColorInfo info);
    void addCat(CatDto catDto);
    void updateCat(CatDto catDto);
    void delete(long id);
    void addFriend(List<Long> message);
    Cat getCatById(long id);
}
