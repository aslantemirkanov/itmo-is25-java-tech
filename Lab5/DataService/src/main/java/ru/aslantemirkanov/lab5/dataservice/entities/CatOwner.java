package ru.aslantemirkanov.lab5.dataservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cat_owners", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Data
public class CatOwner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Past
    private LocalDate birthDate;
    @OneToMany(mappedBy = "catOwner",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Cat> cats;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public CatOwner() {
    }

    public CatOwner(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.cats = new ArrayList<>();
    }

    public CatOwner(String name, LocalDate birthDate, List<Cat> ownerCats) {
        this.name = name;
        this.birthDate = birthDate;
        this.cats = ownerCats;
        this.user = null;
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
