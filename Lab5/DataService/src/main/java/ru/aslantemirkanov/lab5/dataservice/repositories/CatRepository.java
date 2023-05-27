package ru.aslantemirkanov.lab5.dataservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aslantemirkanov.lab5.dataservice.entities.Cat;
import ru.aslantemirkanov.lab5.dataservice.entities.CatColor;

import java.util.Set;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    public Set<Cat> findCatByColor(CatColor catColor);

    public Set<Cat> getFriendsById(Long id);
}
