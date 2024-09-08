package website.ALP.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
    
    @Column(name = "quests_completed")
    private int questsCompleted;

    public Leaderboard() {
    }

    public Leaderboard(Users user, int questsCompleted) {
        this.user = user;
        this.questsCompleted = questsCompleted;
    }

    // Getters and setters
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getQuestsCompleted() {
        return questsCompleted;
    }

    public void setQuestsCompleted(int questsCompleted) {
        this.questsCompleted = questsCompleted;
    }
}
