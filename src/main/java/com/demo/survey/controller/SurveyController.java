package com.demo.survey.controller;

import com.demo.survey.models.Question;
import com.demo.survey.models.Survey;
import com.demo.survey.models.support.Response;
import com.demo.survey.models.support.SurveyResult;
import com.demo.survey.service.QuestionService;
import com.demo.survey.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    private final SurveyService surveyService;
    private final QuestionService questionService;

    public SurveyController(SurveyService surveyService, QuestionService questionService) {
        this.surveyService = surveyService;
        this.questionService = questionService;
    }

    @GetMapping(value = "/{surveyId}")
    public ResponseEntity<Survey> findSurvey(@PathVariable("surveyId") Long surveyId) {
        return new ResponseEntity<>(surveyService.findSurvey(surveyId), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Survey> createSurvey(@Valid @RequestBody Survey survey) {
        return new ResponseEntity<>(surveyService.saveSurvey(survey), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{surveyId}/add-questions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Survey> addQuestionToSurvey(@PathVariable("surveyId") Long surveyId, @RequestBody List<Long> questions) {
        Survey survey = surveyService.findSurvey(surveyId);
        Set<Question> questionsToAdd = new HashSet<>();
        questions.forEach(question -> {
            Question validQuestion = questionService.find(question);
            questionsToAdd.add(validQuestion);
        });
        survey.getQuestions().addAll(questionsToAdd);
        return new ResponseEntity<>(surveyService.saveSurvey(survey), HttpStatus.CREATED);
    }

    @PostMapping(value = "{surveyId}/submit-response")
    public ResponseEntity<?> submitSurvey(@PathVariable("surveyId") Long surveyId, @RequestBody List<Response> responses) {
        Survey survey = surveyService.findSurvey(surveyId);
        surveyService.submitSurveyResponse(survey, responses);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "surveyId")
    public ResponseEntity<Void> deleteSurvey(@PathVariable("surveyId") Long surveyId) {
        surveyService.delete(surveyId);
        return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping(value = "{surveyId}/questions/{questionId}/statistics")
   public ResponseEntity<SurveyResult> getQuestionResult(@PathVariable("surveyId") Long surveyId,
                                                         @PathVariable("questionId") Long questionId) {
        return new ResponseEntity<>(surveyService.getSurveyStatistics(surveyId, questionId), HttpStatus.OK);
   }
}