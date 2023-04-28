package ru.aslantemirkanov.lab3.application.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aslantemirkanov.lab3.application.dto.CatDto;
import ru.aslantemirkanov.lab3.application.dto.CatOwnerDto;
import ru.aslantemirkanov.lab3.application.exception.owner.NoneExistCatOwnerException;
import ru.aslantemirkanov.lab3.application.mapping.CatOwnerMapping;
import ru.aslantemirkanov.lab3.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatColor;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatOwner;
import ru.aslantemirkanov.lab3.dataaccess.repository.CatOwnerRepository;
import ru.aslantemirkanov.lab3.dataaccess.repository.CatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatOwnerServiceImpl implements CatOwnerService {
    @Autowired
    CatOwnerRepository catOwnerRepository;

    @Autowired
    CatRepository catRepository;

    @Autowired
    CatService catService;


    @Override
    public List<CatOwnerDto> findAll() {
        List<CatOwner> catOwners = catOwnerRepository.findAll();
        List<CatOwnerDto> catOwnerDtos = new ArrayList<>();
        for (CatOwner catOwner : catOwners) {
            catOwnerDtos.add(CatOwnerMapping.asCatOwnerDto(catOwner));
        }
        return catOwnerDtos;
    }

    @Override
    public CatOwnerDto findById(long id) {
        CatOwner catOwner = getCatOwner(id);
        return CatOwnerMapping.asCatOwnerDto(catOwner);
    }

    @Override
    @Transactional
    public void createCatOwner(CatOwnerDto catOwnerDto) {
        CatOwner catOwner = CatOwnerMapping.asCatOwner(catOwnerDto);
        catOwnerRepository.save(catOwner);
    }

    @Override
    @Transactional
    public void addOwner(long catOwnerId, long catId) {
        Cat cat = catService.getCatById(catId);
        CatOwner catOwner = getCatOwner(catOwnerId);
        catOwner.getCats().add(cat);
        cat.setCatOwner(catOwner);
        catOwnerRepository.save(catOwner);
    }

    @Override
    public List<CatDto> getCatsById(long id) {
        return findById(id).getCats();
    }

    @Override
    public List<CatDto> getCatsByColor(long id, CatColor color) {
        return null;
    }

    @Override
    @Transactional
    public void deleteCatOwner(long id) {
        List<Cat> cats = getCatOwner(id).getCats();
        for (Cat cat : cats) {
            catService.delete(cat.getId());
        }
        catOwnerRepository.deleteById(id);
    }

    public CatOwner getCatOwner(long id) {
        return catOwnerRepository.findById(id).orElseThrow(()
                -> new NoneExistCatOwnerException(id));
    }
}
