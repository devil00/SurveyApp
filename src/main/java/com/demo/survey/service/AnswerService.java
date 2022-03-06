package com.demo.survey.service;

import com.demo.survey.models.Answer;
import com.demo.survey.models.Question;
import com.demo.survey.repository.AnswerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer find(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isEmpty()) {
            throw new ResourceNotFoundException("Question with id " + id + " not found.");
        }
        return answer.get();
    }

    public Page<Answer> findAll(Pageable pageable) {
        return answerRepository.findAll(pageable);
    }

    public Answer update(Answer answer) {
        Answer existingAnswer = find(answer.getId());
        if(answer.equals(existingAnswer)) {
            return answer;
        }
        return saveAnswer(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public List<Answer> findAnswersByQuestion(Question question) {
        return this.answerRepository.findByQuestion(question);
    }

}
