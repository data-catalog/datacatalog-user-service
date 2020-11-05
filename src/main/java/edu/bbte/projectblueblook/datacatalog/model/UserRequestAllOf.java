package edu.bbte.projectblueblook.datacatalog.model;

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
 * UserRequestAllOf
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-29T12:17:48.213780400+02:00[Europe/Bucharest]")

public class UserRequestAllOf  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("password")
  private String password;

  public UserRequestAllOf password(String password) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserRequestAllOf userRequestAllOf = (UserRequestAllOf) o;
    return Objects.equals(this.password, userRequestAllOf.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserRequestAllOf {\n");
    
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

