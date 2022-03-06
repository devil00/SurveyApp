package com.demo.survey.repository;

import com.demo.survey.models.SurveyResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "survey-response", path = "survey-response")
public interface SurveyResponseRepository extends PagingAndSortingRepository<SurveyResponse, Long> {
    @Query("SELECT s.answerId, count(s.answerId) FROM SurveyResponse s WHERE s.surveyId = :surveyId and s.questionId = :questionId GROUP BY s.surveyId, s.questionId, s.answerId")
    List<Object[]> loadStatistics(@Param("surveyId") Long surveyId, @Param("questionId")  Long questionId);

    @Query("SELECT count(s.questionId) FROM SurveyResponse s WHERE s.surveyId = :surveyId and s.questionId = :questionId GROUP BY s.surveyId, s.questionId")
    long countByQuestionId(@Param("surveyId") Long surveyId, @Param("questionId")  Long questionId);
}
