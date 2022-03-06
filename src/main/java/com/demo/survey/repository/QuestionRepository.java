package com.demo.survey.repository;

import com.demo.survey.models.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "questions", path = "questions")
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
}
