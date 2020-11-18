package edu.bbte.projectbluebook.datacatalog.users.model;

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
 * InlineResponse200
 */

public class InlineResponse200  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("active")
  private Boolean active;

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("username")
  private String username;

  @JsonProperty("exp")
  private Integer exp;

  @JsonProperty("iat")
  private Integer iat;

  public InlineResponse200 active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * This is a boolean value of whether or not the presented token is currently active.
   * @return active
  */
  @ApiModelProperty(required = true, value = "This is a boolean value of whether or not the presented token is currently active.")
  @NotNull


  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public InlineResponse200 clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * The unique identifier for the user that the token was issued to.
   * @return clientId
  */
  @ApiModelProperty(value = "The unique identifier for the user that the token was issued to.")

@Size(min=1) 
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public InlineResponse200 username(String username) {
    this.username = username;
    return this;
  }

  /**
   * The username of the user that the token was issued to.
   * @return username
  */
  @ApiModelProperty(example = "user1", value = "The username of the user that the token was issued to.")

@Size(min=1) 
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public InlineResponse200 exp(Integer exp) {
    this.exp = exp;
    return this;
  }

  /**
   * The unix timestamp indicating when this token will expire.
   * @return exp
  */
  @ApiModelProperty(example = "1437275311", value = "The unix timestamp indicating when this token will expire.")


  public Integer getExp() {
    return exp;
  }

  public void setExp(Integer exp) {
    this.exp = exp;
  }

  public InlineResponse200 iat(Integer iat) {
    this.iat = iat;
    return this;
  }

  /**
   * Unix timestamp indicating when this token was originally issued.
   * @return iat
  */
  @ApiModelProperty(example = "1419350238", value = "Unix timestamp indicating when this token was originally issued.")


  public Integer getIat() {
    return iat;
  }

  public void setIat(Integer iat) {
    this.iat = iat;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(this.active, inlineResponse200.active) &&
        Objects.equals(this.clientId, inlineResponse200.clientId) &&
        Objects.equals(this.username, inlineResponse200.username) &&
        Objects.equals(this.exp, inlineResponse200.exp) &&
        Objects.equals(this.iat, inlineResponse200.iat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(active, clientId, username, exp, iat);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    exp: ").append(toIndentedString(exp)).append("\n");
    sb.append("    iat: ").append(toIndentedString(iat)).append("\n");
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

