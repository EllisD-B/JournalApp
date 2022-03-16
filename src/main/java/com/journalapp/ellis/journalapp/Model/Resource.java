package com.journalapp.ellis.journalapp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "resource")
public class Resource {

    @JsonProperty("name")
    @Column(name = "resource_name")
    private String resourceName;

    @JsonProperty("url")
    @Column(name = "resource_url")
    private String resourceUrl;

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

    public Resource(long id, String name, String url) {
        this.id = id;
        this.resourceName = name;
        this.resourceUrl = url;
    }

    public Resource(String name, String url) {
        this.resourceName = name;
        this.resourceUrl = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String name) {
        this.resourceName = name;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String url) {
        this.resourceUrl = url;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + resourceName + '\'' +
                ", url='" + resourceUrl + '\'' +
                ", id=" + id +
                '}';
    }

}
