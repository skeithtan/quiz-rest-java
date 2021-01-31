package fr.epita.quiz_rest.models.repositories;

import fr.epita.quiz_rest.models.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
