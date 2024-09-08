package website.ALP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import website.ALP.Service.admin.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @DeleteMapping("/user/email/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        try {
            adminService.deleteUserByEmail(email);
            return ResponseEntity.ok("User with email '" + email + "' and associated leaderboard entries deleted successfully.");
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
        }
    }

    @DeleteMapping("/user/id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            adminService.deleteUserById(id);
            return ResponseEntity.ok("User with ID '" + id + "' and associated leaderboard entries deleted successfully.");
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
        }
    }
}
