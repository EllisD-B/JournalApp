package com.journalapp.ellis.journalapp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "resource")
@Builder
public class Resource {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("tags")
    @ElementCollection
    private List<String> tags;

    @Id
    @SequenceGenerator(
            name="resource_sequence",
            sequenceName = "resource_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "resource_sequence"
    )
    private Long id;

    public Resource() {

    }

    public Resource(long id, String name, String url, List<String> tags) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.tags = tags;
    }

    public Resource(String name, String url, List<String> tags) {
        this.name = name;
        this.url = url;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", tags=" + tags +
                ", id=" + id +
                '}';
    }

}
