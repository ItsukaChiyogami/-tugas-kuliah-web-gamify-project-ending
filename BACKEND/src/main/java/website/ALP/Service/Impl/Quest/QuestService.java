package website.ALP.Service.Impl.Quest;

import website.ALP.Dto.QuestDto;

import java.util.List;

public interface QuestService {
    List<QuestDto> getAllQuests();
    QuestDto getQuestById(Long questId);
    QuestDto createQuest(QuestDto questDto);
    QuestDto updateQuest(Long questId, QuestDto questDto);
    void deleteQuest(Long questId);
    List<QuestDto> getQuestsByCategory(String category);
    List<QuestDto> getQuestsByDeadlineBefore(Long days);
    List<QuestDto> searchQuests(Long questId, String category, Integer days, Integer months, Integer years);
    void addCategory(String category);
}
