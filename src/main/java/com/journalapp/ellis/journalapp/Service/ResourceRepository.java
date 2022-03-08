package com.journalapp.ellis.journalapp.Service;

import com.journalapp.ellis.journalapp.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("Select r FROM Resource r WHERE r.name = ?1")
    Optional<Resource> findResourceByName(String name);
}
