package fr.epita.quiz_rest.models.datatransfer;

import java.util.Set;

public class QuizToEvaluateDTO {
    private Integer parentQuizId;
    private Set<QuestionToEvaluateDTO> questions;
    private String examineeName;

    public Integer getParentQuizId() {
        return parentQuizId;
    }

    public void setParentQuizId(Integer parentQuizId) {
        this.parentQuizId = parentQuizId;
    }

    public Set<QuestionToEvaluateDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionToEvaluateDTO> questions) {
        this.questions = questions;
    }

    public String getExamineeName() {
        return examineeName;
    }

    public void setExamineeName(String examineeName) {
        this.examineeName = examineeName;
    }
}
