package fr.epita.quiz_rest.models.entities;

import javax.persistence.*;

@Entity
public class EvaluatedQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String examineeName;

    private Integer examineeScore;

    private Integer totalQuestionsCount;

    @ManyToOne
    private Quiz parentQuiz;

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

    public void setExamineeScore(Integer totalScore) {
        this.examineeScore = totalScore;
    }

    public Quiz getParentQuiz() {
        return parentQuiz;
    }

    public void setParentQuiz(Quiz parentQuiz) {
        this.parentQuiz = parentQuiz;
    }

    public Integer getTotalQuestionsCount() {
        return totalQuestionsCount;
    }

    public void setTotalQuestionsCount(Integer totalQuestionsCount) {
        this.totalQuestionsCount = totalQuestionsCount;
    }
}
