package fr.epita.quiz_rest.services;

import fr.epita.quiz_rest.models.datatransfer.QuestionToEvaluateDTO;
import fr.epita.quiz_rest.models.datatransfer.QuizToEvaluateDTO;
import fr.epita.quiz_rest.models.entities.EvaluatedQuiz;
import fr.epita.quiz_rest.models.entities.Question;
import fr.epita.quiz_rest.models.entities.QuestionChoice;
import fr.epita.quiz_rest.models.entities.Quiz;
import fr.epita.quiz_rest.models.repositories.EvaluatedQuizRepository;
import fr.epita.quiz_rest.models.repositories.QuestionRepository;
import fr.epita.quiz_rest.models.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Configurable
public class QuizEvaluatorService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final EvaluatedQuizRepository repository;

    @Autowired
    public QuizEvaluatorService(QuizRepository quizRepository, QuestionRepository questionRepository, EvaluatedQuizRepository repository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.repository = repository;
    }

    private enum QuestionEvaluationResult {
        CORRECT, INCORRECT
    }

    private QuestionEvaluationResult evaluateQuestion(Question question, QuestionToEvaluateDTO toEvaluate) {
        var selectedAnswers = new HashSet<>(toEvaluate.getSelectedChoicesIds());
        var correctAnswers = question.getChoices()
                .stream()
                .filter(QuestionChoice::isValid)
                .map(QuestionChoice::getId)
                .collect(Collectors.toSet());

        if (selectedAnswers.size() != correctAnswers.size()) {
            return QuestionEvaluationResult.INCORRECT;
        }

        for (var answer: correctAnswers) {
            if (!selectedAnswers.contains(answer)) {
                return QuestionEvaluationResult.INCORRECT;
            }
        }

        return QuestionEvaluationResult.CORRECT;
    }

    public EvaluatedQuiz evaluateAndSave(QuizToEvaluateDTO dto) {
        return repository.save(evaluate(dto));
    }

    public EvaluatedQuiz evaluate(QuizToEvaluateDTO dto) {
        var quiz = quizRepository.findById(dto.getParentQuizId()).orElseThrow();
        var totalScore = (int) dto.getQuestions().stream()
                .map(questionToEvaluateDTO -> {
                    var question = questionRepository
                            .findById(questionToEvaluateDTO.getParentQuestionId())
                            .orElseThrow();

                    return evaluateQuestion(question, questionToEvaluateDTO);
                })
                .filter(result -> result == QuestionEvaluationResult.CORRECT)
                .count();

        var evaluatedQuiz = new EvaluatedQuiz();
        evaluatedQuiz.setParentQuiz(quiz);
        evaluatedQuiz.setExamineeName(dto.getExamineeName());
        evaluatedQuiz.setExamineeScore(totalScore);
        evaluatedQuiz.setTotalQuestionsCount(quiz.getQuestions().size());

        return evaluatedQuiz;
    }

    public List<EvaluatedQuiz> getEvaluationsForQuizWithId(Integer id) {
        var example = new EvaluatedQuiz();
        var exampleQuiz = new Quiz();
        exampleQuiz.setId(id);
        example.setParentQuiz(exampleQuiz);
        return repository.findAll(Example.of(example));
    }
}
