package threee.User;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

  private UserService userService;

  @Autowired
  public UserRestController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/user")
  public ResponseEntity<?> registration(@RequestBody threee.User.UserDTO user) {
    try {
      userService.restRegister(user.getConfirm(), user);
      return ResponseEntity.status(201).body("Created");
    } catch (SyntaxException e) {
      return ResponseEntity.status(400).body("Failed");
    }
  }
}
