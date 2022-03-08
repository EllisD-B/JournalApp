package com.journalapp.ellis.journalapp.Service;

import com.journalapp.ellis.journalapp.Model.QueryStatus;
import com.journalapp.ellis.journalapp.Model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ResourceService {

    private final ResourceRepository repository;
    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.repository = resourceRepository;
    }

    public List<Resource> getResources() {
        return repository.findAll();
    }

    public Optional<Resource> saveResource(Resource r) {
        Optional<Resource> resource = repository.findResourceByName(r.getName());

        if(resource.isPresent()) {
            log.error("Resource with name {} already present, cannot save", r.getName());
            return Optional.empty();
        }

        return Optional.of(repository.save(r));
    }

    public QueryStatus deleteResource(Resource r) {
        Optional<Resource> resource = repository.findResourceByName(r.getName());
        QueryStatus status;

        if(resource.isEmpty()) {
            log.error("Resource with name {} does not exist", r.getName());
            return QueryStatus.FAILURE;
        }
        repository.delete(r);
        return QueryStatus.SUCCESS;
    }
}
