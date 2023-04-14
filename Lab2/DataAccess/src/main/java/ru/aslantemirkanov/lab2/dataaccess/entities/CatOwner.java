package ru.aslantemirkanov.lab2.dataaccess.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cat_owners", catalog = "Lab2", schema = "tables")
@Setter
@Getter
public class CatOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "catOwner", fetch = FetchType.EAGER)
    private List<Cat> cats;

    public CatOwner() {
    }

    public CatOwner(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        cats = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CatOwner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate.toString() +
                '}';
    }
}
