package ru.aslantemirkanov.lab5.dataservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
//@Table(name = "cats", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Table(name = "cats")
@Setter
@Getter
public class Cat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Past
    private LocalDate birthDate;
    private String breed;
    @ManyToMany
    @JoinTable(name = "cat_friends",
            joinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private List<Cat> catFriends;
    @Enumerated(EnumType.STRING)
    private CatColor color;
    @ManyToOne
    private CatOwner catOwner;

    public Cat() {
    }

    public Cat(String name, String breed, CatColor color, LocalDate birthDate) {
        this.color = color;
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
        this.catFriends = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate.toString() +
                ", breed='" + breed + '\'' +
                ", color=" + color.toString() +
                '}';
    }
}