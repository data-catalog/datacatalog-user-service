package edu.bbte.projectbluebook.datacatalog.users.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApiKeyCreationResponse
 */

public class ApiKeyCreationResponse  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("title")
  private String title;

  @JsonProperty("id")
  private String id;

  @JsonProperty("key")
  private String key;

  @JsonProperty("createdAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  public ApiKeyCreationResponse title(String title) {
    this.title = title;
    return this;
  }

  /**
   * A name for the API key. Should be unique for the user.
   * @return title
  */
  @ApiModelProperty(value = "A name for the API key. Should be unique for the user.")


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ApiKeyCreationResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The ID of the api key.
   * @return id
  */
  @ApiModelProperty(value = "The ID of the api key.")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ApiKeyCreationResponse key(String key) {
    this.key = key;
    return this;
  }

  /**
   * The api key. It is only sent once, after the creation of the api key.
   * @return key
  */
  @ApiModelProperty(value = "The api key. It is only sent once, after the creation of the api key.")


  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public ApiKeyCreationResponse createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * The date of creation.
   * @return createdAt
  */
  @ApiModelProperty(value = "The date of creation.")

  @Valid

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiKeyCreationResponse apiKeyCreationResponse = (ApiKeyCreationResponse) o;
    return Objects.equals(this.title, apiKeyCreationResponse.title) &&
        Objects.equals(this.id, apiKeyCreationResponse.id) &&
        Objects.equals(this.key, apiKeyCreationResponse.key) &&
        Objects.equals(this.createdAt, apiKeyCreationResponse.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, id, key, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiKeyCreationResponse {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

