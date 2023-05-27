package ru.aslantemirkanov.lab3.application.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.application.exception.cat.NoneExistCatException;
import ru.aslantemirkanov.lab3.application.mapping.CatDtoMapping;
import ru.aslantemirkanov.lab3.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatColor;
import ru.aslantemirkanov.lab3.dataaccess.repository.CatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@ComponentScan("ru.aslantemirkanov.lab3.dataaccess.repository")
public class CatServiceImpl implements CatService {
    @Autowired
    CatRepository catRepository;

    @Override
    public CatDto getCatDtoById(long id) {
        Cat cat = getCatById(id);
        return CatDtoMapping.asCatDto(cat);
    }

    @Override
    public List<CatDto> getAllFriends(long id) {
        List<CatDto> catDtoList = new ArrayList<>();
        Set<Cat> catSet = catRepository.getFriendsById(id);
        for (Cat cat : catSet) {
            catDtoList.add(CatDtoMapping.asCatDto(cat));
        }
        return catDtoList;
    }

    @Override
    public List<CatDto> getAllCats() {
        List<Cat> cats = catRepository.findAll();
        List<CatDto> catDtoList = new ArrayList<>();
        for (Cat cat : cats) {
            catDtoList.add(CatDtoMapping.asCatDto(cat));
        }
        return catDtoList;
    }

    @Override
    public List<CatDto> getCatsDtoByColor(CatColor color) {
        List<Cat> cats = catRepository.findAll();
        List<CatDto> catDtoList = new ArrayList<>();
        for (Cat cat : cats) {
            if (cat.getColor().equals(color)) {
                catDtoList.add(CatDtoMapping.asCatDto(cat));
            }
        }
        return catDtoList;
    }

    @Override
    @Transactional
    public void addCat(CatDto catDto) {
        Cat cat = CatDtoMapping.asCat(catDto);
        catRepository.save(cat);
    }

    @Override
    @Transactional
    public void updateCat(CatDto catDto) {
        Cat cat = getCatById(catDto.getId());
        cat.setName(catDto.getName());
        cat.setColor(catDto.getColor());
        cat.setBreed(catDto.getBreed());
        catRepository.save(cat);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Cat deleteCat = getCatById(id);
        List<Cat> catFriends = deleteCat.getCatFriends();
        for (Cat catFriend : catFriends) {
            List<Cat> currentCatFriends = catFriend.getCatFriends();
            currentCatFriends.remove(deleteCat);
        }
        catRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addFriend(long idFirst, long idSecond) {
        if (idFirst == idSecond) {
            return;
        }
        Cat catFirst = getCatById(idFirst);
        Cat catSecond = getCatById(idSecond);
        catFirst.getCatFriends().add(catSecond);
        catSecond.getCatFriends().add(catFirst);
        catRepository.save(catFirst);
        catRepository.save(catSecond);
    }

    public void changeNameById(long id, String name) {
        CatDto catDto = getCatDtoById(id);
        catDto.setName(name);
        updateCat(catDto);
    }

    public void changeBreedById(long id, String breed) {
        CatDto catDto = getCatDtoById(id);
        catDto.setBreed(breed);
        updateCat(catDto);
    }

    public void changeColorById(long id, CatColor color) {
        CatDto catDto = getCatDtoById(id);
        catDto.setColor(color);
        updateCat(catDto);
    }

    public Cat getCatById(long id) {
        return catRepository.findById(id).orElseThrow(()
                -> new NoneExistCatException(id));
    }

}
