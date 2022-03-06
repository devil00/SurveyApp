package com.demo.survey.controller;

import com.demo.survey.models.Answer;
import com.demo.survey.service.AnswerService;
import com.demo.survey.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Answer> save(@Valid @RequestBody Answer answer, @RequestParam Long questionId) {
        return new ResponseEntity<>(questionService.addAnswerToQuestion(answer,
                questionService.find(questionId)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Answer> getAnswer(@PathVariable Long id) {
        return new ResponseEntity<>(answerService.find(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Answer> updateAnswer(@Valid Answer answer, @PathVariable("id") Long ansId) {
        return new ResponseEntity<>(answerService.saveAnswer(answer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Answer answer = answerService.find(id);
        answerService.delete(answer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
