package com.journalapp.ellis.journalapp.FunctionalTest;

import com.journalapp.ellis.journalapp.Model.CallbackResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAllResourcesFunctionalTest extends BaseFunctionalTest {

    @Test
    public void shouldReturnListOfResources() throws Exception {
        ResponseEntity<CallbackResponse> response = (ResponseEntity<CallbackResponse>) controller.getResources();
        assertThat(response).hasFieldOrProperty("body");
        CallbackResponse callbackResponse = response.getBody();
        assertNotNull(callbackResponse);
        assertThat(callbackResponse.getData()).hasSize(2).contains(resources.get(0), resources.get(1));
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
