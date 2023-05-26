package ru.aslantemirkanov.lab5.dataservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aslantemirkanov.lab5.dataservice.entities.CatOwner;

@Repository
public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
}
