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

    public Optional<Resource> getResourceById(long id) {
        return repository.findById(id);
    }

    public Optional<Resource> getResourceByName(Resource r) {
        return repository.findResourceByName(r.getResourceName());
    }

    public Optional<Resource> saveResource(Resource r) {
        Optional<Resource> resource = repository.findResourceByName(r.getResourceName());

        if(resource.isPresent()) {
            log.error("Resource with name {} already present, cannot save", r.getResourceName());
            return Optional.empty();
        }

        log.info("Resource with name {} being created", r.getResourceName());
        return Optional.of(repository.save(r));
    }

    public Optional<Resource> updateResource(Resource r) {
        Optional<Resource> resource = repository.findById(r.getId());

        if(resource.isPresent()) {
            repository.updateResource(r.getResourceName(), r.getResourceUrl(), resource.get().getId());
            log.info("resource successfully updated");
            return Optional.of(r);
        }

        log.error("Resource with name {} not found", r.getResourceName());
        return Optional.empty();
    }

    public QueryStatus deleteResource(Resource r) {
        Optional<Resource> resource = repository.findResourceByName(r.getResourceName());

        if(resource.isEmpty()) {
            log.error("Resource with name {} does not exist", r.getResourceName());
            return QueryStatus.FAILURE;
        }
        repository.delete(r);
        log.info("Successfully deleted resource");
        return QueryStatus.SUCCESS;
    }
}
