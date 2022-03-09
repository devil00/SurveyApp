# SurveyApp

This is a simple survey application. It  gives the ability for simple polls comprising questions with multiple choice answers.
It is built using springboot and uses H2 database.

Context -

This document contains the design approach for developing survey application . This application is developed in Spring and follow the REST skeleton.
A survey application comprises the list of questions  having multiple choice answers assigned to user. A user will pick one choice for each question,
proceeding this application will allow the user to submit the response.

Environment:

Java 11
H2 Database - In-memory DB, which means this application will not retain the store once the app is stopped.


Design:
This application follows REST architecture  controller to add required endpoints, service for implementing business logic, and repository to interact with databases.

controller - SurveyController, QuestionController, AnswerController
services - SurveyService,QuestionService, AnswerService
Repository - SurveyRespository, AnswerRepository, SurveyRepository, SurveyResponseRepository
Models - Question, Answer, Survey, SurveyResponse,

DB design
question Table , answer Table, survey Table, survey_questions table , survey_response table

OneTomany relationship Question -> Answer (with foreign key to question-id)

ManyToMany Question - Survey

survey_response - table to store survey responses


REST Endpoint

1. Endpoint to perform add/edit/delete/list for questions

/questions


2. Endpoint to perform add/edit/delete/list for answers

/answers


3. Endpoint to perform add/edit/delete/list/get for surveys

/survey

4. Endpoint to add answer to a question
   /answers?questionId=1

Request body

{"answer": <string>} in json

5. Endpoint to read a question with all answers
   /questions/<question-id>/answers

6. Add questions to a survey

survey/<question-id>/add-questions

7. Submit survey responses

/survey/<survey-id>/submit-response

8. Get the relative distribution of a selected answer by question

/survey/<survey-id>/questions/<question-id>/statistics


Swagger link:

Swagger docs can also be opened through the below link:

/swagger-ui/index.html