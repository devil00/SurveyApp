package com.demo.survey.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "survey")
@ToString(of = {"name", "description", "created"})
public class Survey extends BaseModel {

    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters.")
    @NotNull(message = "Please provide a name")
    private String name;

    @Size(max = 500, message = "The description can't be longer than 500 characters.")
    @NotNull(message = "Please, provide a description")
    private String description;

    /**
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions;
    **/

    @Column(insertable = false, updatable = false)
    @JsonProperty("createdOn")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date created;

    @JsonIgnore
    private Boolean isPublished;

    @ManyToMany(targetEntity = Question.class, cascade = {CascadeType.ALL})
    @JoinTable(name = "survey_questions", joinColumns = {@JoinColumn(name = "s_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "q_id", referencedColumnName = "id")})
    private Set<Question> questions = new HashSet<>();
}
