package com.demo.survey.models.support;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private Long question;
    private Long selectedAnswer;
}
