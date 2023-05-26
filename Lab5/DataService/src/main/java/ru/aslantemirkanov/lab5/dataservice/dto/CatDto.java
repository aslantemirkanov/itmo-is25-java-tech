package ru.aslantemirkanov.lab5.dataservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.aslantemirkanov.lab5.dataservice.entities.CatColor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class CatDto implements Serializable {
    private long id;
    @NotNull
    @NotEmpty
    private String name;
    @Past
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate birthDate;
    private String breed;
    CatColor color;

    CatOwnerDto catOwnerDto;

    public CatDto(){
    }

    public CatDto(String name, String breed, CatColor color, LocalDate birthDate, CatOwnerDto catOwnerDto) {
        this.id = Long.MIN_VALUE;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.birthDate = birthDate;
        this.catOwnerDto = catOwnerDto;
    }

    public CatDto(String name, String breed) {
        this.id = Long.MIN_VALUE;
        this.name = name;
        this.breed = breed;
        this.color = CatColor.Blue;
        this.birthDate = LocalDate.now();
    }
    public CatDto(String name, String breed, CatColor color, LocalDate birthDate, long id) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.birthDate = birthDate;
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
                color == catDto.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, breed, color);
    }
}
