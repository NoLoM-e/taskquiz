package com.company.taskquiz.controller;

import com.company.taskquiz.Routs;
import com.company.taskquiz.entity.dto.response.UserResponse;
import com.company.taskquiz.service.UserOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routs.LEADERS)
public class LeaderBoardController {

    private final UserOperations userOperations;

    public LeaderBoardController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @GetMapping("/{num}")
    public List<UserResponse> getLeaders(@PathVariable Long num){
        return userOperations.getLeaders(num);
    }
}
