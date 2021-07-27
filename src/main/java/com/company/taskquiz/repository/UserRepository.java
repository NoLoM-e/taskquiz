package com.company.taskquiz.repository;

import com.company.taskquiz.entity.QuizUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<QuizUser, Long> {

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    Optional<QuizUser> findByLogin(String login);
    @Query("select u from QuizUser u order by u.score desc")
    List<QuizUser> findLeaders();
}
