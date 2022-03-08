package com.journalapp.ellis.journalapp.Controller;

import com.journalapp.ellis.journalapp.Model.CallbackResponse;
import com.journalapp.ellis.journalapp.Model.QueryStatus;
import com.journalapp.ellis.journalapp.Model.Resource;
import com.journalapp.ellis.journalapp.Service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/callbacks")
public class Controller {

    private final ResourceService resourceService;

    @GetMapping("/resources")
    public ResponseEntity<?> getResources() {
        List<Resource> resources = resourceService.getResources();
        CallbackResponse response = CallbackResponse.builder().data(resources).build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/new-resource")
    public ResponseEntity<?> createResource(
            @RequestBody Resource resource
    ) {
        Optional<Resource> r = resourceService.saveResource(resource);

        return r.map(value -> ResponseEntity.ok(CallbackResponse
                .builder()
                .data(List.of(value))
                .build()))

                .orElseGet(() -> ResponseEntity.ok(CallbackResponse
                .builder()
                .errors(List.of("Already a resource with that name."))
                .build()));

    }

    @DeleteMapping("/remove-resource")
    public ResponseEntity<?> deleteResource(
            @RequestBody Resource resource
    ) {
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

}
