package website.ALP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import website.ALP.model.Leaderboard;
import website.ALP.model.Users;

import java.util.List;

@Repository
public interface LeaderboardRepo extends JpaRepository<Leaderboard, Long> {

    @Query("SELECT lb FROM Leaderboard lb JOIN lb.user u ORDER BY u.points DESC")
    List<Leaderboard> findTopLeaderboardByPointsDesc();

    Leaderboard findByUser(Users user);
    
    void deleteByUser_Id(Long userId);

    List<Leaderboard> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
