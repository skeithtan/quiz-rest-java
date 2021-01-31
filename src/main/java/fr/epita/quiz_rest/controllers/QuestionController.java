package fr.epita.quiz_rest.controllers;

import fr.epita.quiz_rest.models.datatransfer.QuestionDTO;
import fr.epita.quiz_rest.models.repositories.QuestionRepository;
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
@RequestMapping("/questions")
@EnableAutoConfiguration
public class QuestionController {
    private final QuestionRepository repository;

    @Autowired
    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<QuestionDTO> getAllQuestions(@RequestParam Optional<String> topic) {
        return repository.findAll()
                .stream()
                .filter(question -> topic.isEmpty() || question.hasTopic(topic.get()))
                .map(QuestionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping
    public QuestionDTO createQuestion(@Validated @RequestBody QuestionDTO questionDTO) {
        var question = repository.save(questionDTO.toEntity());
        return QuestionDTO.fromEntity(question);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> updateQuestion(@Validated @RequestBody QuestionDTO questionDTO,
                                                      @PathVariable("questionId") Integer questionId) {
        return repository.findById(questionId)
                .map(question -> {
                    var entity = questionDTO.toEntity();
                    entity.setId(questionId);
                    return new ResponseEntity<>(QuestionDTO.fromEntity(repository.save(entity)), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable("questionId") Integer questionId) {
        return repository.findById(questionId)
                .map(question -> new ResponseEntity<>(QuestionDTO.fromEntity(question), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
