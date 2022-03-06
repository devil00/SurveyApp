package com.demo.survey.controller;

import com.demo.survey.models.Answer;
import com.demo.survey.models.Question;
import com.demo.survey.service.AnswerService;
import com.demo.survey.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/questions")
@Validated
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return  "Hello World !";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> save(@Valid @RequestBody  Question question) {
        return new ResponseEntity<>(questionService.saveQuestion(question), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(questionService.find(id), HttpStatus.OK);
    }

    @GetMapping
    public Page<Question> getAllQuestions(Pageable pageable) {
        return questionService.findAll(pageable);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Question> updateQuestion(@Valid Question question, @PathVariable("id") Long questionId) {
        return new ResponseEntity<>(questionService.saveQuestion(question), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Question question = questionService.find(id);
        questionService.delete(question);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/answers")
    public ResponseEntity<List<Answer>> findAnswers(@PathVariable Long id) {
        Question question = this.questionService.find(id);
        return new ResponseEntity<>(this.answerService.findAnswersByQuestion(question), HttpStatus.OK);
    }
}
