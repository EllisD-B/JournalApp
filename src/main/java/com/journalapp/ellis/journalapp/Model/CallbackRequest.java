package com.journalapp.ellis.journalapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackRequest {

    @JsonProperty("data")
    private List<Resource> data;

    @JsonProperty("event_id")
    private String eventId;
}
