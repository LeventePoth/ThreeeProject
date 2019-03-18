package threee.User;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private boolean activated;
  private boolean administrator;
  private String companyName;
  private String profilePicUrl;


  public Users() {
  }

  public Users(String firstName, String lastName, String email, String password, boolean activated, boolean administrator, String companyName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.activated = activated;
    this.administrator = administrator;
    this.companyName = companyName;
  }

  public Users(String firstName, String lastName, String email, String password, String companyName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.companyName = companyName;
  }
}
