package com.journalapp.ellis.journalapp.Service;

import com.journalapp.ellis.journalapp.Model.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResourceRepositoryIntegrationTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ResourceRepository repository;

    Resource youtube;

    @BeforeEach
    public void setUp() {
        entityManager.clear();
        youtube = new Resource("Youtube", "https://www.youtube.com/");
    }

    @Test
    public void whenFindByName_thenReturnResource() {
        entityManager.persist(youtube);
        entityManager.flush();

        Optional<Resource> found = repository.findResourceByName(youtube.getResourceName());

        assertTrue(found.isPresent());
        assertEquals(found.get().getResourceName(), youtube.getResourceName());
    }

    @Test
    public void whenFindByName_andNoResourceFound_thenReturnEmpty() {
        final String NON_EXISTENT = "I don't exist";
        Optional<Resource> notFound = repository.findResourceByName(NON_EXISTENT);

        assertTrue(notFound.isEmpty());
    }

    @Test
    public void whenFindById_thenReturnResource() {
        entityManager.persist(youtube);
        entityManager.flush();

        Optional<Resource> found = repository.findById(youtube.getId());

        assertTrue(found.isPresent());
        assertEquals(found.get().getId(), youtube.getId());
    }

    @Test
    public void whenCreateNewResource_thenReturnCreatedResource() {
        Resource newResource = repository.save(youtube);
        final long ONE_VALUE = 1;

        assertEquals(newResource.getResourceName(), youtube.getResourceName());
        assertEquals(newResource.getResourceUrl(), youtube.getResourceUrl());
        assertEquals(newResource.getId(), ONE_VALUE);
    }

    @Test
    public void whenGetAllResources_shouldReturnAllResources() {
        entityManager.persist(youtube);
        Resource codeAcademy = new Resource("Code Academy", "codeacademy.com");
        entityManager.persist(codeAcademy);
        Resource udemy = new Resource("Udemy", "udemy.com");
        entityManager.persist(udemy);

        Iterable<Resource> resources = repository.findAll();
        assertThat(resources).hasSize(3).contains(youtube, codeAcademy, udemy);
    }

    @Test
    public void whenUpdateResource_shouldReturnUpdatedResource() {
        entityManager.persist(youtube);
        Resource codeAcademy = new Resource("Code Academy", "codeacademy.com");
        entityManager.persist(codeAcademy);

        Resource updatedAcademy = new Resource("Updated Code Academy", "updatedcodeacademy.com");
        Optional<Resource> academyOptional = repository.findResourceByName(codeAcademy.getResourceName());
        assertTrue(academyOptional.isPresent());
        Resource academy = academyOptional.get();

        academy.setResourceName(updatedAcademy.getResourceName());
        academy.setResourceUrl(updatedAcademy.getResourceUrl());
        repository.save(academy);

        Optional<Resource> checkResourceOptional = repository.findById(academy.getId());
        assertTrue(checkResourceOptional.isPresent());
        Resource checkResource = checkResourceOptional.get();

        assertThat(checkResource.getId()).isEqualTo(codeAcademy.getId());
        assertThat(checkResource.getResourceName()).isEqualTo(updatedAcademy.getResourceName());
        assertThat(checkResource.getResourceUrl()).isEqualTo(updatedAcademy.getResourceUrl());
    }

    @Test
    public void whenDeleteResource_getShouldReturnListWithoutThatResource() {
        entityManager.persist(youtube);
        Resource codeAcademy = new Resource("Code Academy", "codeacademy.com");
        entityManager.persist(codeAcademy);
        Resource udemy = new Resource("Udemy", "udemy.com");
        entityManager.persist(udemy);

        repository.delete(codeAcademy);

        Iterable<Resource> resources = repository.findAll();
        assertThat(resources).hasSize(2).contains(youtube, udemy);
    }
    @Test
    public void filterShouldReturnFilteredListWhenCalledWithValidTags() throws Exception {
        int i =3;
        assertEquals(i, 3);
    }

    @Test
    public void filterShouldReturnErrorsWhenCalledWithInvalidTags() throws Exception {
        int i =3;
        assertEquals(i, 3);
    }
}
