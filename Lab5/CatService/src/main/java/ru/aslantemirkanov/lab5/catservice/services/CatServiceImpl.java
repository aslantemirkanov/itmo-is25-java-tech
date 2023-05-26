package ru.aslantemirkanov.lab5.catservice.services;

import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.aslantemirkanov.lab5.dataservice.dto.CatColorInfo;
import ru.aslantemirkanov.lab5.dataservice.dto.CatDto;
import ru.aslantemirkanov.lab5.dataservice.entities.Cat;
import ru.aslantemirkanov.lab5.dataservice.exceptions.cat.NoneExistCatException;
import ru.aslantemirkanov.lab5.dataservice.mapping.CatDtoMapping;
import ru.aslantemirkanov.lab5.dataservice.repositories.CatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@ComponentScan("ru.aslantemirkanov.lab5.dataservice.*")
@EnableRabbit
public class CatServiceImpl implements CatService {
    @Autowired
    CatRepository catRepository;

    @Override
    @Transactional
    @RabbitListener(queues = "catRegisterQueue")
    public void addCat(CatDto catDto) {
        Cat cat = CatDtoMapping.asCat(catDto);
        catRepository.save(cat);
    }

    @Override
    @Transactional
    @RabbitListener(queues = "catFriendRegisterQueue")
    public void addFriend(List<Long> message) {
        long idFirst = message.get(0);
        long idSecond = message.get(1);
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


    @Override
    @RabbitListener(queues = "getCatByIdQueue")
    public CatDto getCatDtoById(long id) {
        Cat cat = getCatById(id);
        return CatDtoMapping.asCatDto(cat);
    }

    @Override
    @RabbitListener(queues = "getAllCatsQueue")
    public List<CatDto> getAllCats(Long id) {
        List<Cat> cats = catRepository.findAll();
        List<CatDto> catDtoList = new ArrayList<>();
        for (Cat cat : cats) {
            if (cat.getCatOwner() != null && cat.getCatOwner().getUser() != null &&
                    Objects.equals(cat.getCatOwner().getUser().getId(), id)) {
                catDtoList.add(CatDtoMapping.asCatDto(cat));
            }
        }
        return catDtoList;
    }

    @Override
    @RabbitListener(queues = "getCatsByColorQueue")
    public List<CatDto> getCatsDtoByColor(CatColorInfo info) {
        List<Cat> cats = catRepository.findAll();
        List<CatDto> catDtoList = new ArrayList<>();
        for (Cat cat : cats) {
            if (cat.getCatOwner() != null && cat.getCatOwner().getUser() != null &&
                    Objects.equals(cat.getCatOwner().getUser().getId(), info.getUserId()) &&
                cat.getColor().equals(info.getCatColor())) {
                catDtoList.add(CatDtoMapping.asCatDto(cat));
            }
        }
        return catDtoList;
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
    public void updateCat(CatDto catDto) {
        Cat cat = getCatById(catDto.getId());
        cat.setName(catDto.getName());
        cat.setColor(catDto.getColor());
        cat.setBreed(catDto.getBreed());
        catRepository.save(cat);
    }

    public Cat getCatById(long id) {
        return catRepository.findById(id).orElseThrow(()
                -> new NoneExistCatException(id));
    }

}
