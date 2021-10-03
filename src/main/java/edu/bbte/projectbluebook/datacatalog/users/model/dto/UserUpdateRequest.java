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
 * UserUpdateRequest
 */

public class UserUpdateRequest  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("email")
  private String email;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("password")
  private String password;

  public UserUpdateRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * The new e-mail address. Must be unique in the application.
   * @return email
  */
  @ApiModelProperty(example = "example@mail.com", value = "The new e-mail address. Must be unique in the application.")

@javax.validation.constraints.Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserUpdateRequest firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * The new first name.
   * @return firstName
  */
  @ApiModelProperty(example = "Jane", value = "The new first name.")


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UserUpdateRequest lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * The new last name.
   * @return lastName
  */
  @ApiModelProperty(example = "Doe", value = "The new last name.")


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserUpdateRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password of minimum 6 characters, which contains at least a number and a letter
   * @return password
  */
  @ApiModelProperty(example = "password1234", value = "Password of minimum 6 characters, which contains at least a number and a letter")

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
    UserUpdateRequest userUpdateRequest = (UserUpdateRequest) o;
    return Objects.equals(this.email, userUpdateRequest.email) &&
        Objects.equals(this.firstName, userUpdateRequest.firstName) &&
        Objects.equals(this.lastName, userUpdateRequest.lastName) &&
        Objects.equals(this.password, userUpdateRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, firstName, lastName, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserUpdateRequest {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
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

