package com.jg.FindFafik.persistence.dao;

import com.jg.FindFafik.persistence.model.Animal;
import com.jg.FindFafik.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository  extends JpaRepository<Animal, Long> {
    User findByAnimalName(final String animalName);
}
