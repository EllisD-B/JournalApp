package com.journalapp.ellis.journalapp.FunctionalTest;

import com.journalapp.ellis.journalapp.Model.CallbackResponse;
import com.journalapp.ellis.journalapp.Model.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteResourceFunctionalTest extends BaseFunctionalTest {

    @Test
    public void shouldReturnResource_whenDeleteCalledOnPresentData() {
        Resource toDelete = new Resource(1, "Youtube", "https://www.youtube.com/");

        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.deleteResource(toDelete);
        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);

        assertThat(response.getData()).hasSize(1).contains(toDelete);
    }

    @Test
    public void shouldReturnError_whenDeleteCalledOnMissingData() {
        Resource missing = new Resource(8, "Udemy", "udemy.com");

        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.deleteResource(missing);
        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);

        assertThat(response.getData()).isNull();
        assertThat(response.getErrors()).hasSize(1).contains("Resource does not exist");
    }
}
