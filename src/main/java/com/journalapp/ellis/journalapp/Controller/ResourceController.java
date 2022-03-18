package com.journalapp.ellis.journalapp.Controller;

import com.journalapp.ellis.journalapp.Model.CallbackResponse;
import com.journalapp.ellis.journalapp.Model.QueryStatus;
import com.journalapp.ellis.journalapp.Model.Resource;
import com.journalapp.ellis.journalapp.Service.ResourceService;
import com.journalapp.ellis.journalapp.Service.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/callbacks")
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping("/resources")
    public ResponseEntity<?> getResources() {
        log.info("Received callback request to get resources, processing...");
        List<Resource> resources = resourceService.getResources();
        resources.forEach(resource ->
        {
            if(resource.getTags() != null) {
                resource.setTags(Utils.removeTrailingComma(resource.getTags()));
            }
        });
        CallbackResponse response = CallbackResponse.builder().data(resources).build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/resource-by-id")
    public ResponseEntity<?> getResourceById(
            @RequestParam("id") String id
    ) {
        log.info("Received callback request to get resource by id");
        try {
            long resourceId = Long.parseLong(id);

            Optional<Resource> resource = resourceService.getResourceById(resourceId);

            return resource.map(value -> ResponseEntity.ok(CallbackResponse
                    .builder()
                    .data(List.of(value))
                    .build())).orElseGet(() -> ResponseEntity.ok(CallbackResponse
                    .builder()
                    .errors(List.of("Resource with given id not found"))
                    .build()));

        } catch(NumberFormatException nfe) {

            return ResponseEntity.ok(CallbackResponse
                    .builder()
                    .errors(List.of("Error parsing id parameter"))
                    .build());
        }

    }

    @GetMapping("/resource-by-name")
    public ResponseEntity<?> getResourceByName(
            @RequestBody Resource resource
    ) {
        Optional<Resource> resourceOptional = resourceService.getResourceByName(resource);

        return resourceOptional.map(value -> ResponseEntity.ok(CallbackResponse.builder()
                .data(List.of(value))
                .build())).orElseGet(() -> ResponseEntity.ok(CallbackResponse.builder()
                .errors(List.of("couldn't find resource"))
                .build()));
    }

    @GetMapping("/filter-by-tags")
    public ResponseEntity<?> filterByTags(
            @RequestParam String tags
    )  {

        List<String> splitTags = Utils.delimitTags(tags);
        List<Resource> resources = resourceService.filterByTags(splitTags);

        return ResponseEntity.ok(CallbackResponse.builder()
                .data(resources)
                .build());
    }

    @PostMapping("/new-resource")
    public ResponseEntity<?> createResource(
            @RequestBody Resource resource
    ) {
        log.info("Received callback request to add new resource, processing...");
        Optional<Resource> r = resourceService.saveResource(resource);

        return r.map(value -> ResponseEntity.ok(CallbackResponse
                .builder()
                .data(List.of(value))
                .build())).orElseGet(() -> ResponseEntity.ok(CallbackResponse
                .builder()
                .errors(List.of("Already a resource with that name."))
                .build()));

    }

    @PostMapping("/new-resource-tags")
    public ResponseEntity<?> createResourceWithTags(
            @RequestBody Resource resource
    ) {
        log.info("Received callback request to add new resource with tags, processing...");
        Optional<Resource> r = resourceService.saveResource(resource);

        return r.map(value -> ResponseEntity.ok(CallbackResponse
                .builder()
                .data(List.of(value))
                .build())).orElseGet(() -> ResponseEntity.ok(CallbackResponse
                .builder()
                .errors(List.of("Already a resource with that name."))
                .build()));
    }

    @DeleteMapping("/remove-resource")
    public ResponseEntity<?> deleteResource(
            @RequestBody Resource resource
    ) {
        log.info("Received callback request to delete existing resource, processing...");
        if (resourceService.deleteResource(resource).equals(QueryStatus.FAILURE)) {
            return ResponseEntity.ok(
                    CallbackResponse
                            .builder()
                            .errors(List.of("Resource does not exist"))
                            .build()
            );
        }

        return ResponseEntity.ok(
                CallbackResponse
                        .builder()
                        .data(List.of(resource))
                        .build()
        );
    }

    @PutMapping("/update-resource")
    public ResponseEntity<?> updateResource(
            @RequestBody Resource resource
    ) {
        log.info("Received callback request to update existing resource with new name {}, processing...", resource.getResourceName());

        Optional<Resource> r = resourceService.updateResource(resource);

        return r.map(value -> ResponseEntity.ok(CallbackResponse
                        .builder()
                        .data(List.of(value))
                        .build()))

                .orElseGet(() -> ResponseEntity.ok(CallbackResponse
                        .builder()
                        .errors(List.of("Could not find resource to update"))
                        .build()));


    }

}
