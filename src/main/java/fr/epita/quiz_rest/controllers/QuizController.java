package fr.epita.quiz_rest.controllers;

import fr.epita.quiz_rest.models.datatransfer.EvaluatedQuizDTO;
import fr.epita.quiz_rest.models.datatransfer.QuizDTO;
import fr.epita.quiz_rest.models.datatransfer.QuizToEvaluateDTO;
import fr.epita.quiz_rest.models.repositories.QuizRepository;
import fr.epita.quiz_rest.services.QuizCreationService;
import fr.epita.quiz_rest.services.QuizEvaluatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quizzes")
@EnableAutoConfiguration
public class QuizController {
    private final QuizRepository repository;
    private final QuizCreationService creationService;
    private final QuizEvaluatorService evaluatorService;

    @Autowired
    public QuizController(QuizRepository repository,
                          QuizCreationService creationService,
                          QuizEvaluatorService evaluatorService) {
        this.repository = repository;
        this.creationService = creationService;
        this.evaluatorService = evaluatorService;
    }

    @GetMapping
    public List<QuizDTO> getAllQuizzes() {
        return repository.findAll().stream().map(QuizDTO::fromEntity).collect(Collectors.toList());
    }

    @PostMapping
    public QuizDTO createQuiz(@Validated @RequestBody QuizDTO quizDTO) {
        return QuizDTO.fromEntity(creationService.createQuiz(quizDTO));
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable("quizId") Integer quizId,
                                           @RequestParam Optional<Boolean> withAnswers) {
        return repository.findById(quizId)
                .map(quiz -> {
                    var dto = QuizDTO.fromEntity(quiz);

                    if (withAnswers.isEmpty() || Boolean.FALSE.equals(withAnswers.get())) {
                        dto.removeChoiceIsValid();
                    }

                    return new ResponseEntity<>(dto, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizDTO> updateQuiz(@Validated @RequestBody QuizDTO quizDTO,
                                              @PathVariable("quizId") Integer quizId) {
        return repository.findById(quizId)
                .map(quiz -> {
                    var entity = creationService.updateQuiz(quizDTO, quizId);
                    return new ResponseEntity<>(QuizDTO.fromEntity(repository.save(entity)), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{quizId}/evaluations")
    public EvaluatedQuizDTO evaluateQuiz(@Validated @RequestBody QuizToEvaluateDTO dto, @PathVariable Integer quizId) {
        dto.setParentQuizId(quizId);
        return EvaluatedQuizDTO.fromEntity(evaluatorService.evaluateAndSave(dto));
    }

    @GetMapping("/{quizId}/evaluations")
    public List<EvaluatedQuizDTO> getEvaluationsForQuiz(@PathVariable("quizId") Integer quizId) {
        return evaluatorService.getEvaluationsForQuizWithId(quizId)
                .stream()
                .map(EvaluatedQuizDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
