package com.company.taskquiz.service;

import com.company.taskquiz.entity.Course;
import com.company.taskquiz.entity.Task;
import com.company.taskquiz.entity.dto.request.ChangeCoursePermittedRequest;
import com.company.taskquiz.entity.dto.request.TaskRequest;
import com.company.taskquiz.entity.dto.response.TaskResponse;
import com.company.taskquiz.entity.dto.request.CourseRequest;
import com.company.taskquiz.entity.dto.request.UpdateCourseRequest;
import com.company.taskquiz.entity.dto.response.CourseResponse;
import com.company.taskquiz.exceptions.TaskQuizExceptions;
import com.company.taskquiz.repository.CourseRepository;
import com.company.taskquiz.repository.TaskRepository;
import com.company.taskquiz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CourseService implements CourseOperations{

    private final CourseRepository courseRepository;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final Random random;

    public CourseService(CourseRepository courseRepository, TaskRepository taskRepository, UserRepository userRepository, Random random) {
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.random = random;
    }


    @Override
    public Optional<CourseResponse> findById(Long id, Long userId) {
        if(!courseRepository.existsById(id)){
            throw TaskQuizExceptions.courseNotFound(id);
        }
        if(courseRepository.findById(id).get().getAccessed().stream().noneMatch(quizUser -> quizUser.getId().equals(userId))){
            throw TaskQuizExceptions.notPermittedToCourse(userId, id);
        }
        return courseRepository.findById(id).map(CourseResponse::fromCourse);
    }

    @Override
    public Optional<CourseResponse> findByName(String name) {
        return courseRepository.findByName(name).map(CourseResponse::fromCourse);
    }

    @Override
    public List<CourseResponse> findAll() {
        return courseRepository.findAll().stream().map(CourseResponse::fromCourse).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> findByOwnerId(Long id) {
        return courseRepository.findByOwnerId(id).stream().map(CourseResponse::fromCourse).collect(Collectors.toList());
    }

    @Override
    public CourseResponse create(CourseRequest request) {
        if(courseRepository.existsByName(request.getName())){
            throw TaskQuizExceptions.duplicateCourseName(request.getName());
        }
        if(!userRepository.existsById(request.getOwnerId())){
            throw TaskQuizExceptions.userNotFound(request.getOwnerId());
        }

        Course course = new Course();
        course.setName(request.getName());
        course.setTasks(new ArrayList<>());
        for (TaskRequest dto : request.getTasks()) {
            Task task = new Task();
            task.setQuestion(dto.getQuestion());
            task.setAnswer(dto.getAnswer());

            taskRepository.save(task);
            course.getTasks().add(task);
        }
        course.setOwner(userRepository.findById(request.getOwnerId()).get());

        course.setAccessed(new ArrayList<>());
        course.getAccessed().add(userRepository.findById(request.getOwnerId()).get());

        courseRepository.save(course);

        for (Task t : course.getTasks()) {
            t.setCourse(course);
            taskRepository.save(t);
        }

        return CourseResponse.fromCourse(course);
    }

    @Override
    public CourseResponse update(UpdateCourseRequest request) {
        if(!courseRepository.existsById(request.getId())){
            throw TaskQuizExceptions.courseNotFound(request.getId());
        }
        if(!userRepository.existsById(request.getOwnerId())){
            throw TaskQuizExceptions.userNotFound(request.getOwnerId());
        }

        Course course = courseRepository.findById(request.getId()).get();
        course.setName(request.getName());
        course.setTasks(new ArrayList<>());
        for (TaskResponse dto : request.getTasks()) {
            Task task = new Task();
            task.setQuestion(dto.getQuestion());
            task.setAnswer(dto.getAnswer());
            task.setCourse(course);

            taskRepository.save(task);
            course.getTasks().add(task);
        }

        courseRepository.save(course);

        return CourseResponse.fromCourse(course);
    }

    @Override
    public CourseResponse updatePermitted(ChangeCoursePermittedRequest request, Long courseId, Long userId) {
        if(!courseRepository.existsById(courseId)){
            throw TaskQuizExceptions.courseNotFound(courseId);
        }

        Course course = courseRepository.findById(courseId).get();

        if(!userId.equals(course.getOwner().getId())){
            throw TaskQuizExceptions.notAnOwner(userId, courseId);
        }

        request.setAddedIds(request.getAddedIds().stream().distinct().collect(Collectors.toList()));
        request.getAddedIds().removeAll(request.getRemovedIds());

        request.setRemovedIds(request.getRemovedIds().stream().distinct().collect(Collectors.toList()));
        request.getRemovedIds().removeAll(request.getAddedIds());
        request.getRemovedIds().remove(course.getOwner().getId());

        for (Long id: request.getAddedIds()) {
            if(userRepository.existsById(id)) {
                if (course.getAccessed().stream().noneMatch(user -> user.getId().equals(id))) {
                    course.getAccessed().add(userRepository.findById(id).get());
                }
            }
        }

        for (Long id : request.getRemovedIds()) {
            if (userRepository.existsById(id)) {
                if(course.getAccessed().stream().anyMatch(user -> user.getId().equals(id))){
                    course.getAccessed().remove(userRepository.findById(id).get());
                }
            }
        }

        courseRepository.save(course);

        return CourseResponse.fromCourse(course);
    }

    @Override
    public void delete(Long id) {
        if(!courseRepository.existsById(id)){
            throw TaskQuizExceptions.courseNotFound(id);
        }

        courseRepository.deleteById(id);
    }

    @Override
    public List<TaskResponse> generateTest(Long id, Long num) {
        if(!courseRepository.existsById(id)){
            throw TaskQuizExceptions.courseNotFound(id);
        }

        Course course = courseRepository.findById(id).get();

        if(num > course.getTasks().size()){
            throw TaskQuizExceptions.tooManyQuestions(course.getId(), num);
        }

        List<Task> tasks = new ArrayList<>();

        while (tasks.size() < num)
            addTaskToList(tasks, course.getTasks().get(Math.abs(random.nextInt()) % course.getTasks().size()));

        return tasks.stream().map(TaskResponse::fromTask).collect(Collectors.toList());
    }

    private void addTaskToList(List<Task> list, Task task){
        if(!list.contains(task)){
            list.add(task);
        }
    }
}
