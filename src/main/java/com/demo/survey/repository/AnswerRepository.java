package com.demo.survey.repository;

import com.demo.survey.models.Answer;
import com.demo.survey.models.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "answers", path = "answers")
public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long> {
    List<Answer> findByQuestion(Question question);
}
