package ru.aslantemirkanov.lab2.application.services;

import ru.aslantemirkanov.lab2.application.dto.CatColorDto;
import ru.aslantemirkanov.lab2.application.dto.CatDto;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatDAO;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatPostgreDAO;
import ru.aslantemirkanov.lab2.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab2.dataaccess.entities.CatColor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CatService {

    CatDAO catDAO = new CatPostgreDAO();

    public CatDto createCatDto(String name, String breed, CatColorDto color, LocalDate birthDate) {
        long id = catDAO.insertCat(new Cat(name, breed, colorTransferRight(color), birthDate));
        return new CatDto(name, breed, color, birthDate, id);
    }

    public void addFriend(long idFirst, long idSecond) {
        Cat cat1 = catDAO.findById(idFirst);
        Cat cat2 = catDAO.findById(idSecond);
        catDAO.addFriend(cat1, cat2);
        catDAO.addFriend(cat2, cat1);
    }

    public List<CatDto> getAllFriends(long id) {
        Set<Cat> catList = catDAO.findAllFrineds(catDAO.findById(id));
        List<CatDto> catDtoList = new ArrayList<>();
        for (Cat cat : catList) {
            catDtoList.add(new CatDto(cat.getName(), cat.getBreed(), colorTransferBack(cat.getColor()), cat.getBirthDate(), cat.getId()));
        }
        return catDtoList;
    }

    public CatColor colorTransferRight(CatColorDto catColorDto) {
        return switch (catColorDto) {
            case Red -> CatColor.Red;
            case Orange -> CatColor.Orange;
            case Yellow -> CatColor.Yellow;
            case Green -> CatColor.Green;
            case Blue -> CatColor.Blue;
            case Purple -> CatColor.Purple;
            default -> throw new RuntimeException();
        };
    }

    public CatColorDto colorTransferBack(CatColor catColor) {
        return switch (catColor) {
            case Red -> CatColorDto.Red;
            case Orange -> CatColorDto.Orange;
            case Yellow -> CatColorDto.Yellow;
            case Green -> CatColorDto.Green;
            case Blue -> CatColorDto.Blue;
            case Purple -> CatColorDto.Purple;
            default -> throw new RuntimeException();
        };

    }

    public CatDto findCatById(long id) {
        Cat cat = catDAO.findById(id);
        return new CatDto(
                cat.getName(),
                cat.getBreed(),
                colorTransferBack(cat.getColor()),
                cat.getBirthDate(),
                cat.getId());
    }
}
