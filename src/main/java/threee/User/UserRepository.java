package threee.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
  Users save(Users users);
  Users findOneByEmail(String email);
  Users findById(long id);
  List<Users> findAll();
  List<Users> findAllByAdministratorFalse();

  @Query("SELECT p FROM Users p WHERE " +
          "LOWER(p.email)NOT LIKE'%@greenfoxacademy.com%' AND " +
          "(LOWER(p.email)LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
          "LOWER(p.firstName) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
          "LOWER(p.lastName) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
          "LOWER(p.companyName) LIKE LOWER(CONCAT('%',:searchTerm, '%')))")
  List<Users> findBySearchTerm(@Param("searchTerm") String searchTerm);
}
