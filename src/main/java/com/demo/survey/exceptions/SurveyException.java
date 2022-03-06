package com.demo.survey.exceptions;

public class SurveyException extends RuntimeException {
    private static final long serialVersionUID = 6691975913756553829L;
    public SurveyException() {}
    public SurveyException(String msg) {
        super(msg);
    }
}
