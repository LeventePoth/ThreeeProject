package threee.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ApplicationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer id;
  public String username;
  public String password;
  public String roles;

  public ApplicationUser() {
  }

  public ApplicationUser(String username, String password, String roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }
}
