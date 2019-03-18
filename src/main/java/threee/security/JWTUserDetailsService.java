package threee.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import threee.User.UserRepository;
import threee.User.UserService;
import threee.User.Users;

@Component
public class JWTUserDetailsService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;
  UserService userservice;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = loadApplicationUserByName(username);

    user.setPassword("{bcrypt}" + user.getPassword());

    String role="ROLE_USER";
    if (user.isAdministrator()){
      role = "ROLE_ADMIN";
    }

    return new User(user.getEmail(),
        user.getPassword(),
        AuthorityUtils.createAuthorityList(role));
  }

  public Users loadApplicationUserByName(String email){
    return userRepository.findOneByEmail(email);
  }
}
