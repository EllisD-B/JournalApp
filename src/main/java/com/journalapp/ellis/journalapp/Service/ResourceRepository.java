package com.journalapp.ellis.journalapp.Service;

import com.journalapp.ellis.journalapp.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("Select r FROM Resource r WHERE r.resourceName = ?1")
    Optional<Resource> findResourceByName(String name);

    @Query("update Resource r SET r.resourceName = ?1 WHERE r.id = ?2")
    Optional<Resource> updateResourceName(String name, Long id);

    @Modifying
    @Transactional
    @Query("update Resource set resourceName = ?1, resourceUrl = ?2 where id = ?3")
    void updateResource(String name, String url, Long id);

}
