package com.demo.survey.models.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResult {
    private long surveyId;
    private long questionId;
    private long totalResponses;
    private List<AnswerStatistics> answerStatistics = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerStatistics {
        private Long answerId;
        private Double percentage;
    }
}
