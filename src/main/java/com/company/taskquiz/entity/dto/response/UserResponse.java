package com.company.taskquiz.entity.dto.response;

import com.company.taskquiz.entity.Role;
import com.company.taskquiz.entity.QuizUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String email;

    private boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<Role> roles;

    private OffsetDateTime createdAt;

    private Long score;

    public static UserResponse fromUser(QuizUser quizUser){
        UserResponse response = new UserResponse(quizUser.getId(),
                quizUser.getEmail(),
                quizUser.isActive(),
                new ArrayList<Role>(),
                quizUser.getCreatedAt(),
                quizUser.getScore());

        quizUser.getRoles().keySet().forEach(role -> response.roles.add(role));

        return response;
    }
}
