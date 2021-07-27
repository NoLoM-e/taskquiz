package com.company.taskquiz.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "roles")
@JsonSerialize
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Enumerated(EnumType.ORDINAL)
    private Role id;

    @ManyToMany(mappedBy = "roles")
    private Set<QuizUser> quizUsers;

    public Role getId() {
        return id;
    }

    public void setId(Role id) {
        this.id = id;
    }

    public Set<QuizUser> getQuizUsers() {
        return quizUsers;
    }

    public void setQuizUsers(Set<QuizUser> quizUsers) {
        this.quizUsers = quizUsers;
    }
}
