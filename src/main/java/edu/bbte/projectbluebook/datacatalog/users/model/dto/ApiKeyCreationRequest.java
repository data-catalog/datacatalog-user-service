package edu.bbte.projectbluebook.datacatalog.users.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApiKey model sent by the client to the server for creating a new api key.
 */
@ApiModel(description = "ApiKey model sent by the client to the server for creating a new api key.")

public class ApiKeyCreationRequest  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("title")
  private String title;

  public ApiKeyCreationRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * A descriptive name for the new api key. Use a name that will help you recognize this key in your account.
   * @return title
  */
  @ApiModelProperty(required = true, value = "A descriptive name for the new api key. Use a name that will help you recognize this key in your account.")
  @NotNull

@Size(min=1) 
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiKeyCreationRequest apiKeyCreationRequest = (ApiKeyCreationRequest) o;
    return Objects.equals(this.title, apiKeyCreationRequest.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiKeyCreationRequest {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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

