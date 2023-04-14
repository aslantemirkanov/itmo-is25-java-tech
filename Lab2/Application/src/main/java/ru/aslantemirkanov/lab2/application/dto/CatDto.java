package ru.aslantemirkanov.lab2.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class CatDto {
    private long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    CatColorDto color;
    private CatOwnerDto catOwnerDto;

    public CatDto(String name, String breed, CatColorDto color, LocalDate birthDate) {
        this.id = Long.MIN_VALUE;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.birthDate = birthDate;
        catOwnerDto = null;
    }

    public CatDto(String name, String breed, CatColorDto color, LocalDate birthDate, long id) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.birthDate = birthDate;
        catOwnerDto = null;
    }

    @Override
    public String toString() {
        return "CatDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate.toString() +
                ", breed='" + breed + '\'' +
                ", color=" + color.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatDto catDto = (CatDto) o;
        return id == catDto.id && Objects.equals(name, catDto.name) &&
                Objects.equals(birthDate, catDto.birthDate) &&
                Objects.equals(breed, catDto.breed) &&
                color == catDto.color &&
                Objects.equals(catOwnerDto, catDto.catOwnerDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, breed, color, catOwnerDto);
    }
}
