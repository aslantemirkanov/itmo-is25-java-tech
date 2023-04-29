package ru.aslantemirkanov;

import ru.aslantemirkanov.lab2.presentation.controllers.CatController;
import ru.aslantemirkanov.lab2.presentation.controllers.CatOwnerController;

public class Main {
    public static void main(String[] args) {

        CatController catController = new CatController();
        CatOwnerController catOwnerController = new CatOwnerController();
/*        CatDto catDto1 = catController.createCat("myaaaa1", "russia", CatColorDto.Yellow, LocalDate.now());
        CatDto catDto2 = catController.createCat("myaaaa2", "russia", CatColorDto.Yellow, LocalDate.now());
        CatDto catDto3 = catController.createCat("myaaaa3", "russia", CatColorDto.Yellow, LocalDate.now());
        CatDto catDto4 = catController.createCat("myaaaa4", "russia", CatColorDto.Yellow, LocalDate.now());
        CatDto catDto5 = catController.createCat("myaaaa5", "russia", CatColorDto.Yellow, LocalDate.now());
        catController.addFriend(1, 2);
        catController.addFriend(1, 3);
        catController.addFriend(1, 4);
        catController.addFriend(1, 5);
        catController.addFriend(2, 3);
        catController.addFriend(2, 4);
        catController.addFriend(1, 2);
        catController.addFriend(1, 2);
        catController.addFriend(1, 2);
        catController.addFriend(1, 2);
        catController.addFriend(1, 2);
        catController.addFriend(1, 2);
        catController.addFriend(1, 2);
        List<CatDto> catDtoList = catController.getAllFriends(1);*/
        catOwnerController.addCatForOwnerById(1,1);
        catOwnerController.deleteCatOwner(1);
        /*
        for (CatDto catDto : catDtoList) {
            System.out.println(catDto);
        }*/
       /* CatOwnerDto catOwnerDto = catOwnerController.createCatOwnerDto("dad7", LocalDate.now());
        catOwnerController.addCatForOwner(catDto, catOwnerDto);*/
//        catOwnerController.addCatForOwnerById(4, 3);
    }
}