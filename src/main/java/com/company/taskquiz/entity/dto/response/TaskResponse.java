package com.company.taskquiz.entity.dto.response;


import com.company.taskquiz.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private Long id;

    private String question;

    private String answer;

    public static TaskResponse fromTask(Task task){
        return new TaskResponse(task.getId(), task.getQuestion(), task.getAnswer());
    }
}
