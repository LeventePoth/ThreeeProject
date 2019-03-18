package threee.User;


import java.util.List;

public interface UserService {
  boolean isAdminEmail(Users users);
  boolean isPasswordValid(String password, String hashedPassword);
  boolean isAdmin(Users users);
  String passwordEncoder(String password);
  String errorHandler(Users users, String confirm);
  String loginErrorHandler(String email, String password);
  Users save(Users users);
  Users findOneByEmail(String email);
  Users register(Users users);
  Users findById(long id);
  Users restRegister(String confirm, threee.User.UserDTO user);
  List<Users> findAllByAdministratorFalse();
  List<Users> listUsersByActivity(boolean active, boolean inactive, List<Users> searchedUsers);
  boolean isValidPassword(String password, String confirm);
  boolean isValidEmail(String email);
  boolean isInputFieldEmpty(Users users, String confirm);
  boolean isExistingEmail(String email);
  void activation(Users user);
  void setAdminFields(Users users);
}
