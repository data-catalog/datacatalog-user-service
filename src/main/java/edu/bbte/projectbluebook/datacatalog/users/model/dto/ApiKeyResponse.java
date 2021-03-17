package edu.bbte.projectbluebook.datacatalog.users.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApiKey model sent by the server to the client. Note that the actual key is only sent once after the creation of the key, and will only be stored a hased version of it on the server. Make sure to save the key, as it is not recoverable.
 */
@ApiModel(description = "ApiKey model sent by the server to the client. Note that the actual key is only sent once after the creation of the key, and will only be stored a hased version of it on the server. Make sure to save the key, as it is not recoverable.")

public class ApiKeyResponse  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("title")
  private String title;

  @JsonProperty("id")
  private UUID id;

  public ApiKeyResponse title(String title) {
    this.title = title;
    return this;
  }

  /**
   * 
   * @return title
  */
  @ApiModelProperty(value = "")


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ApiKeyResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * The unique identifier of the api key.
   * @return id
  */
  @ApiModelProperty(value = "The unique identifier of the api key.")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiKeyResponse apiKeyResponse = (ApiKeyResponse) o;
    return Objects.equals(this.title, apiKeyResponse.title) &&
        Objects.equals(this.id, apiKeyResponse.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiKeyResponse {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

