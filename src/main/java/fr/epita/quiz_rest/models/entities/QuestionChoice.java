package fr.epita.quiz_rest.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "question_choice")
public class QuestionChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String label;

    private boolean valid;

    public QuestionChoice(Integer id, String label, boolean valid) {
        this.id = id;
        this.label = label;
        this.valid = valid;
    }

    public QuestionChoice() {}

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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
