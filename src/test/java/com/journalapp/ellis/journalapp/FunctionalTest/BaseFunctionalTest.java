package com.journalapp.ellis.journalapp.FunctionalTest;


import com.journalapp.ellis.journalapp.Controller.ResourceController;
import com.journalapp.ellis.journalapp.JournalAppApplication;
import com.journalapp.ellis.journalapp.Model.Resource;
import com.journalapp.ellis.journalapp.Service.ResourceRepository;
import com.journalapp.ellis.journalapp.Service.ResourceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JournalAppApplication.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
abstract class BaseFunctionalTest {

    @Autowired
    ResourceRepository repository;

    @Autowired
    ResourceService resourceService;

    @Autowired
    ResourceController controller;

    List<Resource> resources = new ArrayList<>(List.of(
            new Resource(1,"Youtube", "https://www.youtube.com/"),
            new Resource(2, "Codeacademy", "https://www.codecademy.com/")));

}
