package com.demo.survey.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "questions")
@Getter
@Setter
@JsonIgnoreProperties({"surveys", "answers", "isValid"})
public class Question extends BaseModel{
    @Column(name = "question")
    @NotNull
    @Size(min = 2, max = 150, message = "The question should be between 2 and 150 characters")
    private String question;

    /**
    @ManyToOne
    private Survey survey;
    **/

    @Column(name = "created")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("created")
    private Date createdDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Answer> answers;

    @ManyToMany(mappedBy = "questions")
    private  Set<Survey> surveys;

    private boolean isValid;
}
