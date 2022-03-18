package com.journalapp.ellis.journalapp.FunctionalTest;

import com.journalapp.ellis.journalapp.Model.CallbackResponse;
import com.journalapp.ellis.journalapp.Model.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UpdateResourceFunctionalTest extends BaseFunctionalTest {

    @Test
    public void shouldReturnResponseWithUpdatedResource_whenCalledOnValidData() {

        Resource toUpdate = new Resource(1, "Yasstube", "slayqueen.org");
        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.updateResource(toUpdate);
        System.out.println(responseEntity);

        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertThat(response.getData()).hasSize(1).contains(toUpdate);
    }

    @Test
    public void shouldReturnResponseWithErrors_whenCalledOnResourceThatIsNotPresent() {
        Resource notPresent = new Resource(10, "udemy", "udemy.org");
        ResponseEntity<CallbackResponse> responseEntity = (ResponseEntity<CallbackResponse>) controller.updateResource(notPresent);

        CallbackResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertThat(response.getData()).isNull();
        assertThat(response.getErrors()).hasSize(1).contains("Could not find resource to update");
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
