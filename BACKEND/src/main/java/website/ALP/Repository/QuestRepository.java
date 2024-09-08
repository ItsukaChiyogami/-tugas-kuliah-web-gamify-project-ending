package website.ALP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import website.ALP.model.Quest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {

    List<Quest> findByCategory(String category);

    List<Quest> findByDeadlineBefore(LocalDate deadline);

    @Query("SELECT q FROM Quest q " +
           "WHERE (:questId is null OR q.id = :questId) " +
           "AND (:category is null OR q.category = :category) " +
           "AND (:days is null OR DAY(q.deadline) = :days) " +
           "AND (:months is null OR MONTH(q.deadline) = :months) " +
           "AND (:years is null OR YEAR(q.deadline) = :years)")
    List<Quest> searchQuests(Long questId, String category, Integer days, Integer months, Integer years);
}
