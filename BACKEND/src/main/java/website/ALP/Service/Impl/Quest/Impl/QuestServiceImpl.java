package website.ALP.Service.Impl.Quest.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.ALP.Dto.QuestDto;
import website.ALP.model.Quest;
import website.ALP.Repository.QuestRepository;
import website.ALP.Service.Impl.Quest.QuestService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestServiceImpl implements QuestService {

    @Autowired
    private QuestRepository questRepository;

    @Override
    public List<QuestDto> getAllQuests() {
        List<Quest> quests = questRepository.findAll();
        return quests.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuestDto getQuestById(Long questId) {
        Optional<Quest> questOptional = questRepository.findById(questId);
        if (questOptional.isPresent()) {
            return convertToDto(questOptional.get());
        } else {
            throw new IllegalArgumentException("Quest with ID " + questId + " not found.");
        }
    }

    @Override
    public QuestDto createQuest(QuestDto questDto) {
        if (questDto.getQuestId() != null && questDto.getQuestId() != 0) {
            throw new IllegalArgumentException("Quest ID must not be provided when creating a new Quest.");
        }

        Quest quest = convertToEntity(questDto);
        questRepository.save(quest);
        return convertToDto(quest);
    }

    @Override
    public QuestDto updateQuest(Long questId, QuestDto questDto) {
        Optional<Quest> existingQuestOptional = questRepository.findById(questId);
        if (existingQuestOptional.isPresent()) {
            Quest existingQuest = existingQuestOptional.get();
            existingQuest.setTitle(questDto.getTitle());
            existingQuest.setUrl(questDto.getUrl());
            existingQuest.setDescription(questDto.getDescription());
            existingQuest.setCategory(questDto.getCategory());
            existingQuest.setValidate(questDto.isValidate());
            existingQuest.setDeadline(questDto.getDeadline());
            questRepository.save(existingQuest);
            return convertToDto(existingQuest);
        } else {
            throw new IllegalArgumentException("Quest with ID " + questId + " not found.");
        }
    }

    @Override
    public void deleteQuest(Long questId) {
        Optional<Quest> existingQuestOptional = questRepository.findById(questId);
        existingQuestOptional.ifPresent(questRepository::delete);
    }

    @Override
    public List<QuestDto> getQuestsByCategory(String category) {
        List<Quest> quests = questRepository.findByCategory(category);
        return quests.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestDto> getQuestsByDeadlineBefore(Long days) {
        LocalDate deadline = LocalDate.now().plusDays(days);
        List<Quest> quests = questRepository.findByDeadlineBefore(deadline);
        return quests.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestDto> searchQuests(Long questId, String category, Integer days, Integer months, Integer years) {
        List<Quest> quests = questRepository.searchQuests(questId, category, days, months, years);
        return quests.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addCategory(String category) {
        List<Quest> quests = questRepository.findAll();
        quests.forEach(quest -> {
            quest.setCategory(category);
            questRepository.save(quest);
        });
    }

    private QuestDto convertToDto(Quest quest) {
        QuestDto questDto = new QuestDto();
        questDto.setQuestId(quest.getQuestId());
        questDto.setTitle(quest.getTitle());
        questDto.setUrl(quest.getUrl());
        questDto.setDescription(quest.getDescription());
        questDto.setCategory(quest.getCategory());
        questDto.setValidate(quest.isValidate());
        questDto.setDeadline(quest.getDeadline());
        return questDto;
    }

    private Quest convertToEntity(QuestDto questDto) {
        Quest quest = new Quest();
        quest.setTitle(questDto.getTitle());
        quest.setUrl(questDto.getUrl());
        quest.setDescription(questDto.getDescription());
        quest.setCategory(questDto.getCategory());
        quest.setValidate(questDto.isValidate());
        quest.setDeadline(questDto.getDeadline());
        return quest;
    }
}
