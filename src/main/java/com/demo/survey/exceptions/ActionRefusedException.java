package com.demo.survey.exceptions;

public class ActionRefusedException extends SurveyException {
    public ActionRefusedException() {
        super();
    }
    public ActionRefusedException(String msg) {
        super(msg);
    }
}
