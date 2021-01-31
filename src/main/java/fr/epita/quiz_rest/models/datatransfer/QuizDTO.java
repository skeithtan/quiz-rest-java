package fr.epita.quiz_rest.models.datatransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.epita.quiz_rest.models.entities.Question;
import fr.epita.quiz_rest.models.entities.Quiz;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizDTO implements EntityDataTransferObject<Quiz> {
    private Integer id;
    private String name;
    private Set<QuestionDTO> questions;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Integer> existingQuestions;

    public static QuizDTO fromEntity(Quiz entity) {
        var dto = new QuizDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setQuestions(entity.getQuestions().stream().map(QuestionDTO::fromEntity).collect(Collectors.toSet()));
        return dto;
    }

    @Override
    public Quiz toEntity() {
        var entityQuestions = this.questions == null ?
                new HashSet<Question>() :
                this.questions.stream().map(QuestionDTO::toEntity).collect(Collectors.toSet());

        var entity = new Quiz();
        entity.setId(this.getId());
        entity.setName(this.getName());
        entity.setQuestions(entityQuestions);
        return entity;
    }

    public double getAverageDifficulty() {
        var quizDifficulty = 0;

        if (this.questions == null || this.questions.isEmpty()) {
            return quizDifficulty;
        }

        var questionsCount = this.questions.size();

        for (var question : questions) {
            quizDifficulty += question.getDifficultyLevel();
        }

        return quizDifficulty / (double) questionsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Set<Integer> getExistingQuestions() {
        return existingQuestions;
    }

    public void setExistingQuestions(Set<Integer> existingQuestions) {
        this.existingQuestions = existingQuestions;
    }

    public void removeChoiceIsValid() {
        if (this.questions.isEmpty()) {
            return;
        }

        this.questions.forEach(QuestionDTO::removeChoiceIsValid);
    }
}
