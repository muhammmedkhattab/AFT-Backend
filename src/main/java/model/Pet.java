package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Pet {
  @JsonProperty Long id;
  @JsonProperty Category category;
  @JsonProperty String name;
  @JsonProperty List<String> photosUrls;
  @JsonProperty List<Tag> tags;
  @JsonProperty Status status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getPhotosUrls() {
    return photosUrls;
  }

  public void setPhotosUrls(List<String> photosUrls) {
    this.photosUrls = photosUrls;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
