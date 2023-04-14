import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.aslantemirkanov.lab2.application.dto.CatColorDto;
import ru.aslantemirkanov.lab2.application.dto.CatDto;
import ru.aslantemirkanov.lab2.application.dto.CatOwnerDto;
import ru.aslantemirkanov.lab2.application.services.CatOwnerService;
import ru.aslantemirkanov.lab2.application.services.CatService;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatDAO;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatOwnerDAO;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatOwnerPostgreDAO;
import ru.aslantemirkanov.lab2.dataaccess.dao.CatPostgreDAO;
import ru.aslantemirkanov.lab2.dataaccess.entities.Cat;
import ru.aslantemirkanov.lab2.dataaccess.entities.CatColor;
import ru.aslantemirkanov.lab2.dataaccess.entities.CatOwner;
import ru.aslantemirkanov.lab2.presentation.controllers.CatController;
import ru.aslantemirkanov.lab2.presentation.controllers.CatOwnerController;

import java.time.LocalDate;

public class CatControllerTest {
    @Test
    void TestCats(){
        CatController catController = new CatController();
        CatOwnerController catOwnerController = new CatOwnerController();

        CatDAO catDAO = Mockito.mock(CatPostgreDAO.class);
        CatOwnerDAO catOwnerDAO = Mockito.mock(CatOwnerPostgreDAO.class);
        Cat cat = new Cat("vova111", "british", CatColor.Red, LocalDate.now());
        cat.setId(1);
        CatOwner catOwner = new CatOwner("aslan111", LocalDate.now());
        catOwner.setId(1);
        Mockito.when(catDAO.insertCat(cat)).thenReturn(cat.getId());
        Mockito.when(catOwnerDAO.insertCatOwner(catOwner)).thenReturn(catOwner.getId());
        Assertions.assertEquals(cat.getId(), 1);

    }
}
