package com.demo.survey.models;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "survey_response")
@AllArgsConstructor
public class SurveyResponse extends BaseModel {
    private Long surveyId;
    private Long questionId;
    private Long answerId;
}
