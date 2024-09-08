package website.ALP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import website.ALP.model.Users;

public interface AdminRepo extends JpaRepository<Users, Long> {

    // Menghapus user berdasarkan email
    void deleteByEmail(String email);

    // Menghapus user berdasarkan ID
    void deleteById(Long id);

    // Menemukan user berdasarkan email
    Users findByEmail(String email);
}
