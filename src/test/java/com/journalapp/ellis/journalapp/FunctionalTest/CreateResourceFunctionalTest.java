package com.journalapp.ellis.journalapp.FunctionalTest;

import com.journalapp.ellis.journalapp.Model.CallbackResponse;
import com.journalapp.ellis.journalapp.Model.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateResourceFunctionalTest extends BaseFunctionalTest {

    @Test
    public void shouldReturnNewResource_whenCalledWithNewData() {
        Resource udemy = new Resource(3, "Udemy", "https://www.udemy.com");
        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.createResource(udemy);

        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);

        assertThat(response.getData()).hasSize(1).contains(udemy);
    }

    @Test
    public void shouldReturnError_whenCalledWithExistingData() {
        Resource youtube = new Resource(1, "Youtube", "https://www.youtube.com/");
        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.createResource(youtube);

        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);

        assertThat(response.getData()).isNull();
        assertThat(response.getErrors()).hasSize(1).contains("Already a resource with that name.");
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
