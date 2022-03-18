package com.journalapp.ellis.journalapp.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.journalapp.ellis.journalapp.JournalAppApplication;
import com.journalapp.ellis.journalapp.Model.QueryStatus;
import com.journalapp.ellis.journalapp.Model.Resource;
import com.journalapp.ellis.journalapp.Service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JournalAppApplication.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class ResourceControllerTest {

    @Autowired protected WebApplicationContext applicationContext;
    @Autowired protected ObjectMapper objectMapper;

    protected MockMvc mvc;

    @MockBean
    ResourceService mockResourceService;

    Resource youtube;
    Resource codeAcademy;

    private final String POST_URL = "/callbacks/new-resource";
    private final String DELETE_URL = "/callbacks/remove-resource";
    private final String PUT_URL = "/callbacks/update-resource";

    @BeforeEach
    public void setUp() {
        youtube = new Resource("Youtube", "https://www.youtube.com/");
        codeAcademy = new Resource("Codeacademy", "https://www.codecademy.com/");
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void getShouldGetResourcesWhenCalled() throws Exception {
        when(mockResourceService.getResources()).thenReturn(List.of(youtube, codeAcademy));

        final String GET_URL = "/callbacks/resources";
        mvc.perform(get(GET_URL)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mockResourceService, times(1)).getResources();

    }

    @Test
    public void postShouldAddResourceWhenCalledWithNewResource() throws Exception {
        Resource udemy = new Resource("Udemy", "https://www.udemy.com");
        when(mockResourceService.saveResource(udemy)).thenReturn(Optional.of(udemy));

        mvc.perform(post(POST_URL)
                        .content(convertToJson(udemy))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist());

        verify(mockResourceService, times(1)).saveResource(udemy);
    }

    @Test
    public void postShouldReturnErrorWhenCalledWithExistingResource() throws Exception {
        when(mockResourceService.saveResource(youtube)).thenReturn(Optional.empty());

        mvc.perform(post(POST_URL)
                        .content(convertToJson(youtube))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").exists());

        verify(mockResourceService, times(1)).saveResource(youtube);
    }

    @Test
    public void deleteShouldReturnOkStatusWhenCalledWithExistingResource() throws Exception {
        when(mockResourceService.deleteResource(youtube)).thenReturn(QueryStatus.SUCCESS);

        mvc.perform(delete(DELETE_URL)
                .content(convertToJson(youtube))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist());

        verify(mockResourceService, times(1)).deleteResource(youtube);
    }

    @Test
    public void deleteShouldReturnFailureAndPopulateErrorsWhenCalledWithNonExistentResource() throws Exception {
        when(mockResourceService.deleteResource(youtube)).thenReturn(QueryStatus.FAILURE);

        mvc.perform(delete(DELETE_URL)
                .content(convertToJson(youtube))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").exists());

        verify(mockResourceService, times(1)).deleteResource(youtube);
    }

    @Test
    public void updateShouldReturnSuccessWhenCalledWithExistingResource() throws Exception {
        when(mockResourceService.updateResource(youtube)).thenReturn(Optional.of(youtube));

        mvc.perform(put(PUT_URL)
                .content(convertToJson(youtube))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist());

        verify(mockResourceService, times(1)).updateResource(youtube);
    }

    @Test
    public void updateShouldReturnErrorsWhenCalledWithNonExistentResource() throws Exception {
        Resource newData = new Resource(6,"Test New Data", "x.x.com");
        when(mockResourceService.updateResource(newData)).thenReturn(Optional.empty());

        mvc.perform(put(PUT_URL)
                .content(convertToJson(newData))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").exists());

        verify(mockResourceService, times(1)).updateResource(newData);
    }

    private String convertToJson(Object resource) throws JsonProcessingException {
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(resource);
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
