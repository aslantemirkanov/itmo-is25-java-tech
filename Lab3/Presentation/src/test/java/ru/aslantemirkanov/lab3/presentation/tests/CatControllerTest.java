package ru.aslantemirkanov.lab3.presentation.tests;

/*
@SpringBootTest
class CatControllerTest {
    @Mock
    private CatRepository catRepository;

    @InjectMocks
    private CatServiceImpl catService;

    @Test
    void getCatDtoByIdTest() {
        Cat cat1 = new Cat("aslan", "russia", CatColor.Red, LocalDate.now());
        cat1.setId(1L);

        when(catRepository.findById(1L)).thenReturn(Optional.of(cat1));

        Cat result = catService.getCatById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), 1L);
        Assertions.assertEquals(result.getName(), "aslan");
    }

    @Test
    void addFriendTest(){
        Cat cat2 = new Cat("mila", "british", CatColor.Yellow, LocalDate.now());
        cat2.setId(2L);

        Cat cat3 = new Cat("simba", "african", CatColor.Blue, LocalDate.now());
        cat3.setId(3L);

        when(catRepository.findById(2L)).thenReturn(Optional.of(cat2));
        when(catRepository.findById(3L)).thenReturn(Optional.of(cat3));
        when(catRepository.save(cat2)).thenReturn(cat2);
        when(catRepository.save(cat3)).thenReturn(cat3);
        catService.addFriend(2L,3L);
        Assertions.assertEquals(cat2.getCatFriends().size(), 1);
        Assertions.assertEquals(cat3.getCatFriends().size(), 1);

    }

}*/
