package com.journalapp.ellis.journalapp.FunctionalTest;

import com.journalapp.ellis.journalapp.Model.CallbackResponse;
import com.journalapp.ellis.journalapp.Model.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetResourceFunctionalTest extends BaseFunctionalTest {

    @Test
    //see ResourceConfig for resource to return
    public void shouldReturnResource_whenGivenValidExistingId() {
        String id = "1";
        Resource expectedResponse = new Resource(1, "Youtube", "https://www.youtube.com/");

        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.getResourceById(id);
        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);

        assertThat(response.getData()).hasSize(1).contains(expectedResponse);
    }

    @Test
    public void shouldReturnCorrectError_whenGivenValidNonExistentId() {
        String id = "910";
        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.getResourceById(id);
        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertThat(response.getData()).isNull();
        assertThat(response.getErrors()).hasSize(1).contains("Resource with given id not found");
    }

    @Test
    public void shouldReturnCorrectError_whenGivenInvalidId() {
        String id = "wat";
        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.getResourceById(id);

        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertThat(response.getData()).isNull();
        assertThat(response.getErrors()).hasSize(1).contains("Error parsing id parameter");
    }
}
