package fr.epita.quiz_rest.models.repositories;

import fr.epita.quiz_rest.models.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
