package website.ALP.Controller;

import website.ALP.Service.Leaderboard.LeaderboardService;
import website.ALP.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Points")
@CrossOrigin
public class PointController {

    @Autowired
    private LeaderboardService leaderboardService;

    @PostMapping("/AddPoints")
    public ResponseEntity<String> addPointsByEmailOrId(@RequestParam String emailOrId, @RequestParam int points) {
        Users user = leaderboardService.findUserByEmailOrId(emailOrId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        leaderboardService.addPoints(user.getId(), points);
        leaderboardService.addQuestCompleted(user.getId());
        return ResponseEntity.ok("Points successfully added");
    }


    @PostMapping("/SubtractPoints")
    public ResponseEntity<String> subtractPointsByEmailOrId(@RequestParam String emailOrId, @RequestParam int points) {
        Users user = leaderboardService.findUserByEmailOrId(emailOrId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        try {
            leaderboardService.subtractPoints(user.getId(), points);
            return ResponseEntity.ok("Points successfully subtracted");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/SubtractAllUserPoints")
    public ResponseEntity<String> subtractAllUserPoints() {
        leaderboardService.subtractAllUserPoints();
        return ResponseEntity.ok("All user points successfully subtracted");
    }
}
