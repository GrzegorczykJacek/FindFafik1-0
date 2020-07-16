package com.jg.FindFafik.persistence.dao;

import com.jg.FindFafik.persistence.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Advertisement findByTitle (String title);

    Advertisement findById (long id);

    // Custom query method returns list of advertisements by user id
    @Query("SELECT a FROM Advertisement a WHERE a.user.id = :user_id")
    List<Advertisement> findByUserId(@Param("user_id") Long id);
}
