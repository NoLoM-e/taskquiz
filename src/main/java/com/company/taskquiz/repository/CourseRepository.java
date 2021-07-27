package com.company.taskquiz.repository;

import com.company.taskquiz.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
    @Query("select c from Course c inner join QuizUser as u where u.id = :id")
    List<Course> findByOwnerId(Long id);
    boolean existsByName(String name);
}
