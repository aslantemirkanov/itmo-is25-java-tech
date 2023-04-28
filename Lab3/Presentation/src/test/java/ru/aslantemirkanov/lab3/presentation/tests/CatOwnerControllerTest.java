package ru.aslantemirkanov.lab3.presentation.tests;

/*
@SpringBootTest
public class CatOwnerControllerTest {
    @Mock
    private CatOwnerRepository catOwnerRepository;

    @InjectMocks
    private CatOwnerServiceImpl catOwnerService;

    @Test
    void createOwnerTest() {
        CatOwner catOwner = new CatOwner("aslan", LocalDate.now());
        catOwner.setId(1L);

        when(catOwnerRepository.findById(1L)).thenReturn(Optional.of(catOwner));

        CatOwner result = catOwnerService.getCatOwner(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), 1L);
        Assertions.assertEquals(result.getName(), "aslan");
    }
}
*/
