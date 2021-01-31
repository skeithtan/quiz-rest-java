package fr.epita.quiz_rest.models.repositories;

import fr.epita.quiz_rest.models.entities.EvaluatedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluatedQuizRepository extends JpaRepository<EvaluatedQuiz, Integer> {
}
