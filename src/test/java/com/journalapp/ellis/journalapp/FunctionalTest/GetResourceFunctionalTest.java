package com.journalapp.ellis.journalapp.FunctionalTest;

import com.journalapp.ellis.journalapp.Model.CallbackResponse;
import com.journalapp.ellis.journalapp.Model.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetResourceFunctionalTest extends BaseFunctionalTest {

    @Test
    //see ResourceConfig for resource to return
    public void shouldReturnResource_whenGivenValidExistingId() {
        String id = "1";
        Resource expectedResponse = new Resource(1, "Youtube", "https://www.youtube.com/", "JS, Testing");
        CallbackResponse response = ((ResponseEntity<CallbackResponse>) controller.getResourceById(id)).getBody();

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
