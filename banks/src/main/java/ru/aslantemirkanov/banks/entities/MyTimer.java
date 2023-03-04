package ru.aslantemirkanov.banks.entities;

import ru.aslantemirkanov.banks.services.CentralBank;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Класс MyTimer позволяет перемотать время на указанное количество дней и произвести начисление процентов
 * <p>
 * на банковские счета и обновление текущей даты.
 */
public class MyTimer {

    /**
     * Перематывает время на указанное количество дней, производит начисление процентов на банковские счета
     * и обновляет текущую дату.
     *
     * @param daysCount количество дней, на которое необходимо перемотать время
     */
    public void rewindTime(int daysCount) {
        LocalDate dateNow = LocalDate.now();
        System.out.println(LocalDate.now());
        for (int i = 0; i < daysCount; i++) {
            CentralBank.getInstance().addDayInterest();
            LocalDate dateTime = dateNow.plus(i + 1, ChronoUnit.DAYS);
            if (dateTime.getDayOfMonth() == 1) {
                CentralBank.getInstance().addMonthInterest();
            }

            System.out.println(dateTime);
        }
    }
}




