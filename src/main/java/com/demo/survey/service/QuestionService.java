package com.demo.survey.service;

import com.demo.survey.models.Answer;
import com.demo.survey.models.Question;
import com.demo.survey.repository.QuestionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    public QuestionService(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Answer addAnswerToQuestion(Answer answer, Question question) {
        answer.setQuestion(question);
        return answerService.saveAnswer(answer);
    }

    public Question find(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) {
            throw new ResourceNotFoundException("Question with id " + id + " not found.");
        }
        return question.get();
    }
    public Page<Question> findAll (Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Question update(Question question) {
        Question oldQQuestion = find(question.getId());
        if (oldQQuestion.equals(question)) {
            return question;
        }
        return questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
