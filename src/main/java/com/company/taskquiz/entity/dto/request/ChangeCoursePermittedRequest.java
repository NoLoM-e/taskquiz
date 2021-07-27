package com.company.taskquiz.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChangeCoursePermittedRequest {

    private List<Long> addedIds;

    private List<Long> removedIds;
}
