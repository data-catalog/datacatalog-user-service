package edu.bbte.projectbluebook.datacatalog.users.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserLoginResponse
 */

public class UserLoginResponse  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("token")
  private String token;

  @JsonProperty("user")
  private UserResponse user;

  public UserLoginResponse token(String token) {
    this.token = token;
    return this;
  }

  /**
   * JWT token sent by the server. The client will send this with every request to authenticate themselves.
   * @return token
  */
  @ApiModelProperty(value = "JWT token sent by the server. The client will send this with every request to authenticate themselves.")


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserLoginResponse user(UserResponse user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
  */
  @ApiModelProperty(value = "")

  @Valid

  public UserResponse getUser() {
    return user;
  }

  public void setUser(UserResponse user) {
    this.user = user;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserLoginResponse userLoginResponse = (UserLoginResponse) o;
    return Objects.equals(this.token, userLoginResponse.token) &&
        Objects.equals(this.user, userLoginResponse.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, user);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserLoginResponse {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
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

