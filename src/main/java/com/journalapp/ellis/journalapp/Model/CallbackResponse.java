package com.journalapp.ellis.journalapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackResponse {
    private List<Resource> data;

    private List<String> errors;
}
