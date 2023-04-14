package ru.aslantemirkanov.lab2.dataaccess.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cats", catalog = "Lab2", schema = "tables")
@Setter
@Getter
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "cat_friends", schema = "tables",
            joinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<Cat> catFriends;
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
