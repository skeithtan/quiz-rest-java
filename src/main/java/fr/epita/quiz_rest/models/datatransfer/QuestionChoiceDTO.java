package fr.epita.quiz_rest.models.datatransfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.epita.quiz_rest.models.entities.QuestionChoice;

public class QuestionChoiceDTO implements EntityDataTransferObject<QuestionChoice> {
    private Integer id;
    private String label;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean valid;

    public static QuestionChoiceDTO fromEntity(QuestionChoice entity) {
        var dto = new QuestionChoiceDTO();
        dto.setId(entity.getId());
        dto.setLabel(entity.getLabel());
        dto.setValid(entity.isValid());
        return dto;
    }

    @Override
    public QuestionChoice toEntity() {
        var entity = new QuestionChoice();
        entity.setId(getId());
        entity.setLabel(getLabel());
        entity.setValid(isValid());
        return entity;
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

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public void removeValid() {
        this.valid = null;
    }
}
