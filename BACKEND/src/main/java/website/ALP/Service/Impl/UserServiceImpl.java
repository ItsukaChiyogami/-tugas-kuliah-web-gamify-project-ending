package website.ALP.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import website.ALP.Dto.UsersDto;
import website.ALP.Repository.UserRepo;
import website.ALP.Service.UserService;
import website.ALP.model.Users;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepo userRepo,
            AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Users save(UsersDto usersDto) {
        String encodedPassword = passwordEncoder.encode(usersDto.getPassword());
        Optional<Users> userCheck = userRepo.findByEmail(usersDto.getEmail());

        if (!userCheck.isPresent()) {
            Users users = new Users(
                    usersDto.getUsername(),
                    usersDto.getFirstName(),
                    usersDto.getLastName(),
                    usersDto.getEmail(),
                    encodedPassword);
            return userRepo.save(users);
        } else {
            throw new VerifyError();
        }
    }

    @Override
    public Users getUsers(UsersDto usersDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usersDto.getEmail(),
                            usersDto.getPassword()));
            return userRepo.findByEmail(usersDto.getEmail()).get();
        } catch (Exception e) {
            System.err.println("Terjadi kesalahan saat mengambil pengguna: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Gagal mengambil pengguna", e);
        }
    }
    @Override
    public UsersDto getUserProfile(String email) {
        // Find user by email
        Users user = userRepo.findByEmail(email)
                             .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Create DTO from user entity
        return new UsersDto(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPoints()
        );
    }

    @Override
        public void updateUserProfile(UsersDto usersDto) {
            Users user = userRepo.findByEmail(usersDto.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));

            user.setUsername(usersDto.getUsername());
            user.setFirstName(usersDto.getFirstName());
            user.setLastName(usersDto.getLastName());
            
            // If password is provided and not empty, update the password
            if (usersDto.getPassword() != null && !usersDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(usersDto.getPassword()));
            }

            userRepo.save(user);
        }

}
