package com.company.taskquiz.entity.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AddToUserScoreRequest {

    private Long id;

    private Long value;
}
