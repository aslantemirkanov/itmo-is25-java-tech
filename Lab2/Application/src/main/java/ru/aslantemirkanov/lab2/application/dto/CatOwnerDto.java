package ru.aslantemirkanov.lab2.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CatOwnerDto {
    private long id;
    private String name;
    private LocalDate birthDate;
    private List<CatDto> cats;

    public CatOwnerDto(String name, LocalDate birthDate) {
        this.id = Long.MIN_VALUE;
        this.name = name;
        this.birthDate = birthDate;
        cats = new ArrayList<>();
    }

    public CatOwnerDto(String name, LocalDate birthDate, long id) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        cats = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CatOwner{" +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate.toString() +
                '}';
    }
}
