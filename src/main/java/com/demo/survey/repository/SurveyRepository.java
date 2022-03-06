package com.demo.survey.repository;

import com.demo.survey.models.Survey;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "survey", path = "survey")
public interface SurveyRepository  extends PagingAndSortingRepository<Survey, Long>  {
}
