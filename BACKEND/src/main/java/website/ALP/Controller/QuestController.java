package website.ALP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import website.ALP.Dto.QuestDto;
import website.ALP.Service.Impl.Quest.QuestService;

import java.util.List;

@RestController
@RequestMapping("/quests")
@CrossOrigin
public class QuestController {

    @Autowired
    private QuestService questService;

    @GetMapping
    public ResponseEntity<List<QuestDto>> getAllQuests() {
        List<QuestDto> quests = questService.getAllQuests();
        return ResponseEntity.ok().body(quests);
    }

    @GetMapping("/{questId}")
    public ResponseEntity<?> getQuestById(@PathVariable Long questId) {
        try {
            QuestDto quest = questService.getQuestById(questId);
            if (quest != null) {
                return ResponseEntity.ok().body(quest);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quest with ID " + questId + " not found.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid quest ID: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createQuest(@RequestBody QuestDto questDto) {
        try {
            QuestDto createdQuest = questService.createQuest(questDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        }
    }

    @PutMapping("/{questId}")
    public ResponseEntity<?> updateQuest(@PathVariable Long questId, @RequestBody QuestDto questDto) {
        try {
            QuestDto updatedQuest = questService.updateQuest(questId, questDto);
            if (updatedQuest != null) {
                return ResponseEntity.ok().body(updatedQuest);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quest with ID " + questId + " not found.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        }
    }

    @DeleteMapping("/{questId}")
    public ResponseEntity<Void> deleteQuest(@PathVariable Long questId) {
        try {
            questService.deleteQuest(questId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/category")
    public ResponseEntity<String> addCategory(@RequestBody String category) {
        try {
            questService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        }
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchQuests(
            @RequestParam(required = false) Long questId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer days,
            @RequestParam(required = false) Integer months,
            @RequestParam(required = false) Integer years) {

        try {
            List<QuestDto> quests = questService.searchQuests(questId, category, days, months, years);
            if (!quests.isEmpty()) {
                return ResponseEntity.ok().body(quests);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No quests found with the specified criteria.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        }
    }
}