package fr.epita.quiz_rest.models.datatransfer;

import fr.epita.quiz_rest.models.entities.Question;
import fr.epita.quiz_rest.models.entities.QuestionChoice;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionDTO implements EntityDataTransferObject<Question> {
    private Integer id;
    private String label;
    private String[] topics;
    private Integer difficultyLevel;

    private List<QuestionChoiceDTO> choices;

    public static QuestionDTO fromEntity(Question entity) {
        var dto = new QuestionDTO();
        dto.setId(entity.getId());
        dto.setLabel(entity.getLabel());
        dto.setTopics(entity.getTopics());
        dto.setDifficultyLevel(entity.getDifficultyLevel());

        dto.setChoices(entity.getChoices().stream().map(QuestionChoiceDTO::fromEntity).collect(Collectors.toList()));
        return dto;
    }

    public Question toEntity() {
        var entity = new Question();
        entity.setId(this.getId());
        entity.setLabel(this.getLabel());
        entity.setTopics(this.getTopics());
        entity.setDifficultyLevel(this.getDifficultyLevel());
        entity.setChoices(this.getChoiceEntities());
        return entity;
    }

    private List<QuestionChoice> getChoiceEntities() {
        return this.choices.stream().map(QuestionChoiceDTO::toEntity).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public List<QuestionChoiceDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoiceDTO> choices) {
        this.choices = choices;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void removeChoiceIsValid() {
        this.choices.forEach(QuestionChoiceDTO::removeValid);
    }
}
