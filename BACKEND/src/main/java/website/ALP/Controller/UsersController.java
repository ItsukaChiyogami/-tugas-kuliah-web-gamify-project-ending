package website.ALP.Controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import website.ALP.Config.JwtService;
import website.ALP.Dto.UsersDto;
import website.ALP.Repository.UserRepo;
import website.ALP.Service.Impl.UserServiceImpl;
import website.ALP.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceImpl userService;

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UsersDto usersDto) {
        String jwtToken;
        try {
            Users user = userService.save(usersDto);
            jwtToken = jwtService.generateToken(user);
        } catch (VerifyError e) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UsersDto usersDto, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            Users user = userService.getUsers(usersDto);
            String jwtToken = jwtService.generateToken(user);

            Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // Ensure this is only set in production over HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 1 day
            response.addCookie(jwtCookie);

            responseBody.put("jwtToken", jwtToken);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage());
            responseBody.put("error", "Email or Password incorrect");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UsersDto> getUserProfile(@RequestParam String email) {
        try {
            UsersDto userProfile = userService.getUserProfile(email);
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching user profile: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
        public ResponseEntity<String> updateUserProfile(@RequestBody UsersDto usersDto) {
            try {
                userService.updateUserProfile(usersDto);
                return new ResponseEntity<>("Profile updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error updating user profile: {}", e.getMessage());
                return new ResponseEntity<>("Failed to update profile", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

}
