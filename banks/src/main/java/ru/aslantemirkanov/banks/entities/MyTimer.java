package ru.aslantemirkanov.banks.entities;

import ru.aslantemirkanov.banks.services.CentralBank;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MyTimer {
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





