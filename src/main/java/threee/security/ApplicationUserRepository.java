package threee.security;

import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Integer> {
  ApplicationUser findApplicationUserByUsername(String username);
}
