package ru.aslantemirkanov.lab5.catownerservice.services;

import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.aslantemirkanov.lab5.dataservice.dto.CatOwnerDto;
import ru.aslantemirkanov.lab5.dataservice.entities.Cat;
import ru.aslantemirkanov.lab5.dataservice.entities.CatOwner;
import ru.aslantemirkanov.lab5.dataservice.exceptions.cat.NoneExistCatException;
import ru.aslantemirkanov.lab5.dataservice.exceptions.owner.NoneExistCatOwnerException;
import ru.aslantemirkanov.lab5.dataservice.mapping.CatOwnerMapping;
import ru.aslantemirkanov.lab5.dataservice.repositories.CatOwnerRepository;
import ru.aslantemirkanov.lab5.dataservice.repositories.CatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan("ru.aslantemirkanov.lab5.*")
public class CatOwnerServiceImpl implements CatOwnerService {
    @Autowired
    CatOwnerRepository catOwnerRepository;

    @Autowired
    CatRepository catRepository;

    @Override
    @RabbitListener(queues = "getAllOwnersQueue")
    public List<CatOwnerDto> findAll(String message) {
        List<CatOwner> catOwners = catOwnerRepository.findAll();
        List<CatOwnerDto> catOwnerDtos = new ArrayList<>();
        for (CatOwner catOwner : catOwners) {
            catOwnerDtos.add(CatOwnerMapping.asCatOwnerDto(catOwner));
        }
        return catOwnerDtos;
    }

    @Override
    @Transactional
    @RabbitListener(queues = "registerOwnerQueue")
    public void createCatOwner(CatOwnerDto catOwnerDto) {
        CatOwner catOwner = CatOwnerMapping.asCatOwner(catOwnerDto);
        catOwnerRepository.save(catOwner);
    }

    @Override
    @Transactional
    @RabbitListener(queues = "addOwnerForCatQueue")
    public void addOwner(List<Long> info) {
        Cat cat = catRepository.findById(info.get(1)).orElseThrow(()
                -> new NoneExistCatException(info.get(1)));;
        CatOwner catOwner = getCatOwner(info.get(0));
        catOwner.getCats().add(cat);
        cat.setCatOwner(catOwner);
        catOwnerRepository.save(catOwner);
    }

    @Override
    @RabbitListener(queues = "getOwnerByIdQueue")
    public CatOwnerDto findById(long id) {
        CatOwner catOwner = getCatOwner(id);
        return CatOwnerMapping.asCatOwnerDto(catOwner);
    }

    @Override
    @Transactional
    public void deleteCatOwner(long id) {
        List<Cat> cats = getCatOwner(id).getCats();
        for (Cat cat : cats) {
            //catService.delete(cat.getId());
        }
        catOwnerRepository.deleteById(id);
    }

    public CatOwner getCatOwner(long id) {
        return catOwnerRepository.findById(id).orElseThrow(()
                -> new NoneExistCatOwnerException(id));
    }
}
