package ru.aslantemirkanov.lab3.presentation.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aslantemirkanov.lab3.application.services.CatOwnerServiceImpl;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatOwner;
import ru.aslantemirkanov.lab3.dataaccess.repository.CatOwnerRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

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
