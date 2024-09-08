package website.ALP.Service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import website.ALP.Repository.AdminRepo;
import website.ALP.Repository.LeaderboardRepo;
import website.ALP.Service.admin.AdminService;
import website.ALP.model.Leaderboard;
import website.ALP.model.Users;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private LeaderboardRepo leaderboardRepo;

    @Transactional
    @Override
    public void deleteUserByEmail(String email) {
        Users user = adminRepo.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }

        // Get leaderboard entry if exists
        Optional<Leaderboard> leaderboardEntry = Optional.ofNullable(leaderboardRepo.findByUser(user));

        // Delete leaderboard entry if exists
        leaderboardEntry.ifPresent(leaderboard -> leaderboardRepo.delete(leaderboard));

        // Delete user
        adminRepo.delete(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        Users user = adminRepo.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        // Get leaderboard entry if exists
        Optional<Leaderboard> leaderboardEntry = Optional.ofNullable(leaderboardRepo.findByUser(user));

        // Delete leaderboard entry if exists
        leaderboardEntry.ifPresent(leaderboard -> leaderboardRepo.delete(leaderboard));

        // Delete user
        adminRepo.delete(user);
    }
}
