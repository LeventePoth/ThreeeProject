package threee.User;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private threee.User.UserRepository userRepository;

  @Autowired
  public UserServiceImpl(threee.User.UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Users save(Users users) {
    userRepository.save(users);
    return users;
  }

  @Override
  public Users findOneByEmail(String email) {
    return userRepository.findOneByEmail(email);
  }

  @Override
  public boolean isAdminEmail(Users users) {
    if (users.getEmail().matches("levente.poth@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
      users.setAdministrator(true);
      save(users);
      return true;
    }
    return false;
  }
  
  public Users register(Users users) {
    users.setPassword(passwordEncoder(users.getPassword()));
    save(users);
    return users;
  }

  @Override
  public Users findById(long id) {
    return userRepository.findById(id);
  }

  @Override
  public boolean isValidPassword(String password, String confirm) {
    return password.length() >= 8 && password.equals(confirm);
  }

  @Override
  public boolean isValidEmail(String email) {
    return email.matches("levente.poth@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
            || email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
  }

  @Override
  public boolean isInputFieldEmpty(Users users, String confirm) {
    return users.getFirstName() == null
            || users.getLastName() == null
            || users.getEmail() == null
            || users.getPassword() == null
            || confirm == null
            || users.getCompanyName() == null;
  }

  @Override
  public boolean isExistingEmail(String email) {
    return findOneByEmail(email) == null;
  }

  @Override
  public boolean isPasswordValid(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }

  @Override
  public boolean isAdmin(Users users) {
    return users.isAdministrator();
  }

  @Override
  public void activation(Users user) {
    if (user.isActivated()){
      user.setActivated(false);
    } else {
      user.setActivated(true);
    }
    userRepository.save(user);
  }

  @Override
  public void setAdminFields(Users users) {
    if (isAdminEmail(users)) {
      users.setAdministrator(true);
      users.setActivated(true);
      userRepository.save(users);
    }
  }

  @Override
  public Users restRegister(String confirm, threee.User.UserDTO userDTO) {
    Users users = new Users(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getCompanyName());
    if (errorHandler(users, confirm) != null) {
      throw new SyntaxException("Wrong email or password.");
    } else {
        if (isAdminEmail(users)) {
        setAdminFields(users);
        }
      users.setPassword(passwordEncoder(users.getPassword()));
      userRepository.save(users);
      return users;
    }
  }

  @Override
  public List<Users> findAllByAdministratorFalse() {
    return userRepository.findAllByAdministratorFalse();
  }

  @Override
  public List<Users> listUsersByActivity(boolean active, boolean inactive, List<Users> searchedUsers) {
    List<Users> filteredUsers = new ArrayList<>();
    if (active && inactive)  {
      return searchedUsers;
    } if (active) {
      for (int i = 0; i < searchedUsers.size(); i++) {
        if (searchedUsers.get(i).isActivated()) {
          filteredUsers.add(searchedUsers.get(i));
        }
      }
      return filteredUsers;
    } if (inactive) {
      for (int i = 0; i < searchedUsers.size(); i++) {
        if (!searchedUsers.get(i).isActivated()) {
          filteredUsers.add(searchedUsers.get(i));
        }
      }
      return filteredUsers;
    }
    return searchedUsers;
  }

  @Override
  public String passwordEncoder(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(12));
  }

  @Override
  public String errorHandler(Users users, String confirm) {
    if (!isValidEmail(users.getEmail()) || !isValidPassword(users.getPassword(), confirm)) {
      return "Your email address or password is not correct. Please try again.";
    } else if (isInputFieldEmpty(users, confirm)) {
      return "Please fill all fields";
    } else if (!isExistingEmail(users.getEmail())) {
      return "This email address is already exists.";
    }
    return null;
  }

  @Override
  public String loginErrorHandler(String email, String password) {
    if (findOneByEmail(email) == null || !isPasswordValid(password, findOneByEmail(email).getPassword())) {
      return "Wrong email or password.";
    }
    return null;
  }
}
