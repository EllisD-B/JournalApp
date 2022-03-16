package com.journalapp.ellis.journalapp.Model;

import com.journalapp.ellis.journalapp.Service.ResourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ResourceConfig {

    @Bean
    CommandLineRunner commandLineRunner(ResourceRepository repository) {
        return args -> {
            Resource youtube = new Resource(
                    "Youtube",
                    "https://www.youtube.com/"
            );
            Resource codeAcademy = new Resource(
                    "Codeacademy",
                    "https://www.codecademy.com/"
            );

            repository.saveAll(List.of(youtube, codeAcademy));
        };
    }
}
