package edu.bbte.projectbluebook.datacatalog.users.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserResponse
 */

public class UserResponse  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("email")
  private String email;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("username")
  private String username;

  /**
   * The role of the user.
   */
  public enum RoleEnum {
    ADMIN("ADMIN"),
    
    USER("USER");

    private String value;

    RoleEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RoleEnum fromValue(String value) {
      for (RoleEnum b : RoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("role")
  private RoleEnum role = RoleEnum.USER;

  public UserResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The ID of the user.
   * @return id
  */
  @ApiModelProperty(value = "The ID of the user.")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UserResponse email(String email) {
    this.email = email;
    return this;
  }

  /**
   * The e-mail address of the user.
   * @return email
  */
  @ApiModelProperty(value = "The e-mail address of the user.")

@javax.validation.constraints.Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserResponse firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * The first name of the user.
   * @return firstName
  */
  @ApiModelProperty(value = "The first name of the user.")


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UserResponse lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * The last name of the user.
   * @return lastName
  */
  @ApiModelProperty(value = "The last name of the user.")


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserResponse username(String username) {
    this.username = username;
    return this;
  }

  /**
   * The username of the user.
   * @return username
  */
  @ApiModelProperty(value = "The username of the user.")

@Size(min=3) 
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserResponse role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * The role of the user.
   * @return role
  */
  @ApiModelProperty(value = "The role of the user.")


  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserResponse userResponse = (UserResponse) o;
    return Objects.equals(this.id, userResponse.id) &&
        Objects.equals(this.email, userResponse.email) &&
        Objects.equals(this.firstName, userResponse.firstName) &&
        Objects.equals(this.lastName, userResponse.lastName) &&
        Objects.equals(this.username, userResponse.username) &&
        Objects.equals(this.role, userResponse.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, firstName, lastName, username, role);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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

