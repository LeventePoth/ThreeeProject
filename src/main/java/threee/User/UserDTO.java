package threee.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String companyName;
  private String password;
  private String confirm;

  public UserDTO(String firstName, String lastName, String email, String companyName, String password, String confirm) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.companyName = companyName;
    this.password = password;
    this.confirm = confirm;
  }
}
