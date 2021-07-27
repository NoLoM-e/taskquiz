package com.company.taskquiz.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Getter
@Setter
public class UpdateUserRequest {

    @NotNull(message = "id cannot be null")
    @NotBlank(message = "id cannot be blank")
    private Long id;

    @NotNull(message = "login cannot be null")
    @NotBlank(message = "login cannot be blank")
    private String login;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, max = 100, message = "password size should be between 8 and 100")
    private String password;

    @NotNull(message = "email cannot be null")
    @Email(message = "email should be a valid email string")
    private String email;

    @NotNull
    private OffsetDateTime createdAt;
}
