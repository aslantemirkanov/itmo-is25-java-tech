package ru.aslantemirkanov.lab2.application.services;


import ru.aslantemirkanov.lab2.application.dto.CatColorDto;
import ru.aslantemirkanov.lab2.application.dto.CatDto;
import ru.aslantemirkanov.lab2.application.dto.CatOwnerDto;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatDAO;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatOwnerDAO;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatOwnerPostgreDAO;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatPostgreDAO;
import ru.aslantemirkanov.lab2.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab2.dataaccess.entities.CatColor;
import ru.aslantemirkanov.lab2.dataaccess.entities.CatOwner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatOwnerService {

    CatOwnerDAO catOwnerDAO = new CatOwnerPostgreDAO();
    CatDAO catDAO = new CatPostgreDAO();

    public CatOwnerDto createCatOwnerDto(String name, LocalDate birthDate) {
        long id = catOwnerDAO.insertCatOwner(new CatOwner(name, birthDate));
        return new CatOwnerDto(name, birthDate, id);
    }

    public List<CatOwnerDto> findAll() {
        List<CatOwner> catOwnerList = catOwnerDAO.findAll();
        List<CatOwnerDto> catOwnerDtoList = null;
        for (CatOwner catOwner : catOwnerList) {
            catOwnerDtoList.add(new CatOwnerDto(catOwner.getName(), catOwner.getBirthDate()));
        }
        return catOwnerDtoList;
    }

    public CatOwnerDto findById(long id) {
        CatOwner catOwner = catOwnerDAO.findById(id);
        return new CatOwnerDto(catOwner.getName(), catOwner.getBirthDate());
    }

    public void insertCatOwner(CatOwnerDto catOwnerDto) {
        CatOwner catOwner = new CatOwner(catOwnerDto.getName(), catOwnerDto.getBirthDate());
        catOwnerDAO.insertCatOwner(catOwner);
    }

    public void addOwner(CatDto catDto, CatOwnerDto catOwnerDto) {
        catOwnerDAO.addOwner(catDAO.findById(catDto.getId()), catOwnerDAO.findById(catOwnerDto.getId()));
    }

    public void addOwnerById(long catId, long catOwnerId) {
        catOwnerDAO.addOwnerById(catId, catOwnerId);
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

    public List<CatDto> getCatsById(long id) {
        List<Cat> catList = catOwnerDAO.findById(id).getCats();
        List<CatDto> catDtoList = new ArrayList<>();
        for (Cat cat : catList) {
            catDtoList.add(new CatDto(
                    cat.getName(),
                    cat.getBreed(),
                    colorTransferBack(cat.getColor()),
                    cat.getBirthDate(),
                    cat.getId()));
        }
        return catDtoList;
    }

    public void deleteCatOwner(long id){
        catOwnerDAO.deleteCatOwner(catOwnerDAO.findById(id));
    }


}
