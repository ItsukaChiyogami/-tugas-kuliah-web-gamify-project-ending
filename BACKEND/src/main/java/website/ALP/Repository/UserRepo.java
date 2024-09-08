package website.ALP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import website.ALP.model.Users;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);
    Users getUserProfileByEmail(String email);
    void deleteById(Long id); 
}
