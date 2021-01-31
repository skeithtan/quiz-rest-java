package fr.epita.quiz_rest.models.datatransfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.epita.quiz_rest.models.entities.EvaluatedQuiz;

public class EvaluatedQuizDTO implements EntityDataTransferObject<EvaluatedQuiz> {
    private Integer id;
    private String examineeName;
    private Integer examineeScore;
    private Integer totalQuestionsCount;

    @JsonIgnore
    private QuizDTO parentQuiz;

    @Override
    public EvaluatedQuiz toEntity() {
        var entity = new EvaluatedQuiz();
        entity.setId(this.id);
        entity.setExamineeName(this.examineeName);
        entity.setExamineeScore(this.examineeScore);
        entity.setTotalQuestionsCount(this.totalQuestionsCount);
        entity.setParentQuiz(this.parentQuiz.toEntity());
        return entity;
    }

    public static EvaluatedQuizDTO fromEntity(EvaluatedQuiz entity) {
        var dto = new EvaluatedQuizDTO();
        dto.setId(entity.getId());
        dto.setExamineeName(entity.getExamineeName());
        dto.setExamineeScore(entity.getExamineeScore());
        dto.setTotalQuestionsCount(entity.getTotalQuestionsCount());

        var parentQuiz = entity.getParentQuiz();
        if (parentQuiz != null) {
            dto.setParentQuiz(QuizDTO.fromEntity(parentQuiz));
        }

        return dto;
    }

    @JsonProperty("examineeScorePercentage")
    public Double getExamineeScorePercentage() {
        return this.examineeScore / (double) this.totalQuestionsCount;
    }

    @JsonProperty("parentQuizId")
    public Integer getParentQuizId() {
        return this.getParentQuiz().getId();
    }

    @JsonProperty("parentQuizName")
    public String getParentQuizName() {
        return this.getParentQuiz().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamineeName() {
        return examineeName;
    }

    public void setExamineeName(String examineeName) {
        this.examineeName = examineeName;
    }

    public Integer getExamineeScore() {
        return examineeScore;
    }

    public void setExamineeScore(Integer examineeScore) {
        this.examineeScore = examineeScore;
    }

    public QuizDTO getParentQuiz() {
        return parentQuiz;
    }

    public void setParentQuiz(QuizDTO parentQuiz) {
        this.parentQuiz = parentQuiz;
    }

    public Integer getTotalQuestionsCount() {
        return totalQuestionsCount;
    }

    public void setTotalQuestionsCount(Integer totalQuestionsCount) {
        this.totalQuestionsCount = totalQuestionsCount;
    }
}
