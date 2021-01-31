package fr.epita.quiz_rest.services;

import fr.epita.quiz_rest.models.datatransfer.QuestionDTO;
import fr.epita.quiz_rest.models.datatransfer.QuizDTO;
import fr.epita.quiz_rest.models.entities.Question;
import fr.epita.quiz_rest.models.entities.Quiz;
import fr.epita.quiz_rest.models.repositories.QuestionRepository;
import fr.epita.quiz_rest.models.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Configurable
public class QuizCreationService {
    private final QuizRepository repository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuizCreationService(QuizRepository repository, QuestionRepository questionRepository) {
        this.repository = repository;
        this.questionRepository = questionRepository;
    }

    private Set<Question> getQuestionsWithIds(Set<Integer> ids) {
        return ids.stream()
                .map(questionRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    public Quiz createQuiz(QuizDTO dto) {
        var entity = dto.toEntity();

        if (dto.getExistingQuestions() == null || dto.getExistingQuestions().isEmpty()) {
            return repository.save(entity);
        }

        var quizQuestions = entity.getQuestions();
        var newQuestions = dto.getQuestions();
        var existingQuestions = getQuestionsWithIds(dto.getExistingQuestions());

        if (quizQuestions != null && newQuestions != null) {
            quizQuestions.addAll(newQuestions.stream().map(QuestionDTO::toEntity).collect(Collectors.toSet()));
        }

        if (quizQuestions != null) {
            quizQuestions.addAll(existingQuestions);
            entity.setQuestions(quizQuestions);
        }

        return repository.save(entity);
    }

    public Quiz updateQuiz(QuizDTO dto, Integer quizId) {
        var entity = dto.toEntity();
        entity.setId(quizId);

        var quizQuestions = entity.getQuestions();

        var existingQuestions = getQuestionsWithIds(dto.getExistingQuestions());
        quizQuestions.addAll(existingQuestions);
        entity.setQuestions(quizQuestions);

        return repository.save(entity);
    }
}
