package fr.epita.quiz_rest.models.repositories;

import fr.epita.quiz_rest.models.entities.QuestionChoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionChoiceRepository extends JpaRepository<QuestionChoice, Integer> {
}
