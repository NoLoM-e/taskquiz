package com.company.taskquiz.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class TaskRequest {

    @NotNull(message = "question can't to null")
    @NotBlank(message = "question can't to blank")
    private String question;

    @NotNull(message = "answer can't to null")
    @NotBlank(message = "answer can't to black")
    private String answer;
}
