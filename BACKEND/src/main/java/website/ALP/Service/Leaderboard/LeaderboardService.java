package website.ALP.Service.Leaderboard;

import website.ALP.model.Leaderboard;
import website.ALP.model.Users;

import java.util.List;

public interface LeaderboardService {
    List<Leaderboard> getTopLeaderboard();
    Users addPoints(Long userId, int points); 
    Users subtractPoints(Long userId, int points);
    void addQuestCompleted(Long userId);
    Users findUserByEmailOrId(String emailOrId);
    void subtractAllUserPoints();
    Leaderboard getUserByEmail(String email);
}
