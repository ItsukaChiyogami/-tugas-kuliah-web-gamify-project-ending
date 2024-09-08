package website.ALP.Service.Leaderboard.impl;

import website.ALP.Service.Leaderboard.LeaderboardService;
import website.ALP.model.Leaderboard;
import website.ALP.model.Users;
import website.ALP.Repository.LeaderboardRepo;
import website.ALP.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    @Autowired
    private LeaderboardRepo leaderboardRepo;

    @Autowired
    private UserRepo usersRepo;

    @Override
    public List<Leaderboard> getTopLeaderboard() {
        return leaderboardRepo.findTopLeaderboardByPointsDesc();
    }

    @Override
    @Transactional
    public Users addPoints(Long userId, int points) {
        Users user = usersRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPoints(user.getPoints() + points);
        usersRepo.save(user);
        return user;
    }

    @Override
    @Transactional
    public Users subtractPoints(Long userId, int points) {
        Users user = usersRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPoints() < points) {
            throw new RuntimeException("User does not have enough points to subtract");
        }

        user.setPoints(user.getPoints() - points);
        usersRepo.save(user);
        return user;
    }

    @Override
    @Transactional
    public void addQuestCompleted(Long userId) {
        Users user = usersRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Leaderboard leaderboard = leaderboardRepo.findByUser(user);
        if (leaderboard != null) {
            leaderboard.setQuestsCompleted(leaderboard.getQuestsCompleted() + 1);
        } else {
            leaderboard = new Leaderboard(user, 1);
        }

        leaderboardRepo.save(leaderboard);
    }

    @Override
    public Users findUserByEmailOrId(String emailOrId) {
        try {
            Long userId = Long.parseLong(emailOrId);
            return usersRepo.findById(userId).orElse(null);
        } catch (NumberFormatException e) {
            return usersRepo.findByEmail(emailOrId).orElse(null);
        }
    }

    @Override
    @Transactional
    public void subtractAllUserPoints() {
        List<Users> allUsers = usersRepo.findAll();
        for (Users user : allUsers) {
            user.setPoints(0);
            usersRepo.save(user);
        }
    }

    @Override
    public Leaderboard getUserByEmail(String email) {
        Users user = usersRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return leaderboardRepo.findByUser(user);
    }
}
