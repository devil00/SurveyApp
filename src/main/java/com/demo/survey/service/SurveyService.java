package com.demo.survey.service;

import com.demo.survey.exceptions.ActionRefusedException;
import com.demo.survey.models.Answer;
import com.demo.survey.models.Question;
import com.demo.survey.models.Survey;
import com.demo.survey.models.SurveyResponse;
import com.demo.survey.models.support.Response;
import com.demo.survey.models.support.SurveyResult;
import com.demo.survey.repository.SurveyRepository;
import com.demo.survey.repository.SurveyResponseRepository;
import com.demo.survey.util.SurveyConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final SurveyResponseRepository surveyResponseRepository;

    public SurveyService(SurveyRepository surveyRepository, SurveyResponseRepository surveyResponseRepository,
                         QuestionService questionService, AnswerService answerService) {
        this.surveyRepository = surveyRepository;
        this.surveyResponseRepository = surveyResponseRepository;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey findSurvey(Long id) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (survey.isEmpty()) {
            throw new ResourceNotFoundException("Survey with id " + id + " not found.");
        }
        return survey.get();
    }

    public Page<Survey> findAll(Pageable pageable) {
        return surveyRepository.findAll(pageable);
    }

    public void delete(Long id) {
        Survey survey = findSurvey(id);
        surveyRepository.delete(survey);
    }


    public Survey update(Survey survey) {
        Survey oldSurvey = findSurvey(survey.getId());
        if (oldSurvey.equals(survey)) {
            return survey;
        }
        return surveyRepository.save(survey);
    }

    @Transactional
    public void submitSurveyResponse(Survey survey, List<Response> responses) {
        log.info("Submitting survey {}", survey);
        Survey validSurvey = findSurvey(survey.getId());
        Set<Question> questions = survey.getQuestions();
        Set<Long> surveyQuestions = survey.getQuestions().stream().map(question -> question.getId()).collect(Collectors.toSet());
        List<com.demo.survey.models.SurveyResponse> surveyResponses = new ArrayList<>();
        responses.forEach(response -> {
            Question validQuestion = questionService.find(response.getQuestion());
            if (!questions.contains(validQuestion)) {
                String errMsg = String.format("Not a valid question {} for the given survey {}", validQuestion, survey);
                throw new ActionRefusedException(errMsg);
            }
            Answer validAnswer = answerService.find(response.getSelectedAnswer());
            surveyResponses.add(new SurveyResponse(survey.getId(), validQuestion.getId(), validAnswer.getId()));
        });
        surveyResponseRepository.saveAll(surveyResponses);
    }


    /**
     * Get the relative distribution of a selected answer by question
     * @param surveyId
     * @param questionId
     * @return
     */
    @Transactional(readOnly = true)
    public SurveyResult getSurveyStatistics(Long surveyId, Long questionId) {
        log.info("Finding survey {} statistics by question {}", surveyId, questionId);
        SurveyResult surveyResult = new SurveyResult();
        long totalResponsesByQuestion = surveyResponseRepository.countByQuestionId(surveyId, questionId);
        surveyResult.setTotalResponses(totalResponsesByQuestion);
        surveyResult.setQuestionId(questionId);
        surveyResult.setSurveyId(surveyId);
        if (totalResponsesByQuestion > 0) {
            List<Object[]> data = surveyResponseRepository.loadStatistics(surveyId, questionId);
            for (Object[] objects : data) {
                SurveyResult.AnswerStatistics answerResult = new SurveyResult.AnswerStatistics();
                answerResult.setAnswerId((Long) objects[0]);
                double answersCount = ((Long)objects[1]).doubleValue();
                double percentage = (answersCount * 100) / totalResponsesByQuestion;
                SurveyConstants.DECIMAL_FORMAT.setRoundingMode(RoundingMode.UP);
                answerResult.setPercentage(Double.valueOf(SurveyConstants.DECIMAL_FORMAT.format(percentage)));
                surveyResult.getAnswerStatistics().add(answerResult);
            }
        }

        return surveyResult;
    }
}
