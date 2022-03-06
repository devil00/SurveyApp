package com.demo.survey.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer extends BaseModel {
    @Column(name="answer")
    @Size(min = 1, max = 50, message = "The answer should be less than 50 characters")
    @NotNull(message = "No answer text provided.")
    private String answer;

    @ManyToOne
    @JsonIgnore
    private Question question;

}
