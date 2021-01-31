package fr.epita.quiz_rest.models.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    public static final int MAX_DIFFICULTY = 5;
    public static final int MIN_DIFFICULTY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String label;
    private String[] topics;
    private int difficultyLevel;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionChoice> choices = new ArrayList<>();

    public Question(Integer id, String label, String[] topics, int difficultyLevel) {
        this.id = id;
        this.label = label;
        this.topics = topics;
        this.difficultyLevel = difficultyLevel;
    }

    public Question() {
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

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoice> choices) {
        this.choices = choices;
    }

    public boolean hasTopic(String searchTopic) {
        for (var topic: this.getTopics()) {
            if (topic.equalsIgnoreCase(searchTopic)) {
                return true;
            }
        }

        return false;
    }
}
