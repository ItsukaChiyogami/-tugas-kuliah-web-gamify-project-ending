package website.ALP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.ALP.Service.Leaderboard.LeaderboardService;
import website.ALP.model.Leaderboard;

import java.util.List;

@RestController
@RequestMapping("/Leaderboard")
@CrossOrigin
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping("/Top")
    public List<Leaderboard> getTopLeaderboard() {
        return leaderboardService.getTopLeaderboard();
    }

    @GetMapping("/User")
    public Leaderboard getUserByEmail(@RequestParam String email) {
        return leaderboardService.getUserByEmail(email);
    }
}
