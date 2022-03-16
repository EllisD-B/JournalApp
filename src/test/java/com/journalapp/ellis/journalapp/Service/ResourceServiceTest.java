package com.journalapp.ellis.journalapp.Service;

import com.journalapp.ellis.journalapp.JournalAppApplication;
import com.journalapp.ellis.journalapp.Model.QueryStatus;
import com.journalapp.ellis.journalapp.Model.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JournalAppApplication.class)
@DirtiesContext
@SpringBootTest
public class ResourceServiceTest {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRepository repository;

    private Resource youtube;
    private Resource codeAcademy;

    private Resource newData;

    private List<Resource> storedResources = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        youtube = new Resource("Youtube", "https://www.youtube.com/");
        codeAcademy = new Resource("Codeacademy", "https://www.codecademy.com/");
        storedResources.add(youtube);
        storedResources.add(codeAcademy);
        newData = new Resource( "Udemy", "https://www.udemy.com");
        repository.save(youtube);
        repository.save(codeAcademy);
    }

    @AfterEach
    public void coolDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldReturnListOfResources() {
        List<Resource> data = resourceService.getResources();

        assertTrue(data.containsAll(storedResources));
    }

    @Test
    public void shouldAddUniqueResource() {
        Optional<Resource> addedData = resourceService.saveResource(newData);

        assertTrue(addedData.isPresent());
        assertEquals(addedData.get(), newData);
    }

    @Test
    public void shouldNotAddResourceIfAlreadyPresent() {
        Optional<Resource> emptyData = resourceService.saveResource(youtube);

        assertTrue(emptyData.isEmpty());
    }

    @Test
    public void shouldDeleteResourceIfPresent() {
        QueryStatus queryStatus = resourceService.deleteResource(youtube);

        assertEquals(queryStatus, QueryStatus.SUCCESS);

        List<Resource> data = resourceService.getResources();
        assertFalse(data.contains(youtube));
    }

    @Test
    public void shouldReturnFailureIfResourceNotPresent() {
        QueryStatus queryStatus = resourceService.deleteResource(newData);

        assertEquals(queryStatus, QueryStatus.FAILURE);
    }

    @Test
    public void shouldUpdateResourceWhenPresent() {
        Resource update = new Resource(youtube.getId(), "YT", "yt.com");
        resourceService.updateResource(update);

        assertTrue(repository.findById(youtube.getId()).isPresent());
        assertEquals(repository.findById(youtube.getId()).get(), update);
    }

    @Test
    public void updateShouldReturnEmptyOptionalWhenNotPresent() {
        Resource notPresent = new Resource(100, "asndasj", "ashdiuawhdiu.com");
        Optional<Resource> updated = resourceService.updateResource(notPresent);

        assertTrue(updated.isEmpty());
    }
}
