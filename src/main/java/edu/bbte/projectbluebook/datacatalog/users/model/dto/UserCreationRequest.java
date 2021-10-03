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
 * UserCreationRequest
 */

public class UserCreationRequest  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("email")
  private String email;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  public UserCreationRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * The e-mail address of the user. Must be unique in the application.
   * @return email
  */
  @ApiModelProperty(required = true, value = "The e-mail address of the user. Must be unique in the application.")
  @NotNull

@javax.validation.constraints.Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserCreationRequest firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * The first name of the user.
   * @return firstName
  */
  @ApiModelProperty(required = true, value = "The first name of the user.")
  @NotNull


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UserCreationRequest lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * The last name of the user.
   * @return lastName
  */
  @ApiModelProperty(required = true, value = "The last name of the user.")
  @NotNull


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserCreationRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * The username of the user. This will appear to other users. Must be unique in the application.
   * @return username
  */
  @ApiModelProperty(required = true, value = "The username of the user. This will appear to other users. Must be unique in the application.")
  @NotNull

@Size(min=3) 
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserCreationRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password of minimum 6 characters, which contains at least a number and a letter.
   * @return password
  */
  @ApiModelProperty(example = "password1234", required = true, value = "Password of minimum 6 characters, which contains at least a number and a letter.")
  @NotNull

@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$") @Size(min=6) 
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCreationRequest userCreationRequest = (UserCreationRequest) o;
    return Objects.equals(this.email, userCreationRequest.email) &&
        Objects.equals(this.firstName, userCreationRequest.firstName) &&
        Objects.equals(this.lastName, userCreationRequest.lastName) &&
        Objects.equals(this.username, userCreationRequest.username) &&
        Objects.equals(this.password, userCreationRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, firstName, lastName, username, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserCreationRequest {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

