package com.company.taskquiz.controller;


import com.company.taskquiz.Routs;
import com.company.taskquiz.entity.dto.request.AddToUserScoreRequest;
import com.company.taskquiz.entity.dto.request.UpdateUserRequest;
import com.company.taskquiz.entity.dto.request.UserRequest;
import com.company.taskquiz.entity.dto.response.UserResponse;
import com.company.taskquiz.service.UserOperations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Routs.USERS)
public class UserController {

    private final UserOperations userOperations;

    public UserController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserRequest request){
        return userOperations.create(request);
    }

    @GetMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public UserResponse read(@PathVariable Long id){
        return userOperations.findById(id).orElse(null);
    }

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public List<UserResponse> readAll(){
        return userOperations.findAll();
    }

    @PutMapping
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public UserResponse updateUser(@RequestBody @Valid UpdateUserRequest request){
        return userOperations.update(request);
    }

    @DeleteMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public void deleteUser(@PathVariable Long id){
        userOperations.delete(id);
    }

    @PostMapping("/admins")
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    @Transactional
    public UserResponse createAdmin(@RequestBody @Valid UserRequest request){
        return userOperations.createAdmin(request);
    }

    @PatchMapping("/{id}/score")
    public UserResponse addToScore(@RequestBody @Valid AddToUserScoreRequest request){
        return userOperations.addToScore(request);
    }
}
