package ru.aslantemirkanov.lab3.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CatOwnerDto {
    private long id;
    @NotNull
    @NotEmpty
    private String name;
    @Past
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate birthDate;
    private List<CatDto> cats;

    public CatOwnerDto() {
    }

    public CatOwnerDto(String name, LocalDate birthDate) {
        this.id = Long.MIN_VALUE;
        this.name = name;
        this.birthDate = birthDate;
        cats = new ArrayList<>();
    }

    public CatOwnerDto(String name, LocalDate birthDate, List<CatDto> ownerCats) {
        this.id = Long.MIN_VALUE;
        this.name = name;
        this.birthDate = birthDate;
        cats = ownerCats;
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
