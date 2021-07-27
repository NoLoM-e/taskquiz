package com.company.taskquiz.controller;

import com.company.taskquiz.Routs;
import com.company.taskquiz.entity.QuizUser;
import com.company.taskquiz.entity.UserDetails;
import com.company.taskquiz.entity.dto.request.ChangeCoursePermittedRequest;
import com.company.taskquiz.entity.dto.request.CourseRequest;
import com.company.taskquiz.entity.dto.request.UpdateCourseRequest;
import com.company.taskquiz.entity.dto.response.CourseResponse;
import com.company.taskquiz.entity.dto.response.TaskResponse;
import com.company.taskquiz.service.CourseOperations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Routs.COURSES)
public class CourseController {

    private final CourseOperations courseOperations;

    public CourseController(CourseOperations courseOperations) {
        this.courseOperations = courseOperations;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public CourseResponse create(@RequestBody @Valid CourseRequest courseRequest){
        return courseOperations.create(courseRequest);
    }

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public List<CourseResponse> getAll(){
        return courseOperations.findAll();
    }

    @GetMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public CourseResponse getById(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        return courseOperations.findById(id, userDetails.getSource().getId()).orElse(null);
    }

    @DeleteMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public void deleteCourse(@PathVariable Long id){
        courseOperations.delete(id);
    }

    @PutMapping
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public CourseResponse updateCourse(@RequestBody @Valid UpdateCourseRequest updateCourseRequest){
        return courseOperations.update(updateCourseRequest);
    }

    @PatchMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public CourseResponse changePermitted(@RequestBody @Valid ChangeCoursePermittedRequest request, @PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        return courseOperations.updatePermitted(request, id, userDetails.getSource().getId());
    }

    @GetMapping("/{id}/test/{num}")
    @Operation(security = @SecurityRequirement(name = "basic-scheme"))
    public List<TaskResponse> getTest(@PathVariable Long id, @PathVariable Long num){
        return courseOperations.generateTest(id, num);
    }
}
