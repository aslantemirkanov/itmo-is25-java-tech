import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.aslantemirkanov.lab2.application.dto.CatColorDto;
import ru.aslantemirkanov.lab2.application.dto.CatDto;
import ru.aslantemirkanov.lab2.presentation.controllers.CatController;

import java.time.LocalDate;

public class CatControllerTest {
    @Test
    void TestCatCreating() {
        CatController catController = new CatController();
        CatDto vova = catController.createCat("vova", "british", CatColorDto.Red, LocalDate.now());
        long id = vova.getId();
        CatDto catTest = catController.findById(id);
        assert(vova.equals(catTest));
    }
}
