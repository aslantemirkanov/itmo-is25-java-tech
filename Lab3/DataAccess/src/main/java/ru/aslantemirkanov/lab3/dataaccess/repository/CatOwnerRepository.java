package ru.aslantemirkanov.lab3.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aslantemirkanov.lab3.dataaccess.entities.CatOwner;
@Repository
public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
}
