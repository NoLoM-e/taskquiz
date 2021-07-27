package com.company.taskquiz.service;

import com.company.taskquiz.entity.Role;
import com.company.taskquiz.entity.QuizUser;
import com.company.taskquiz.entity.UserRole;
import com.company.taskquiz.entity.UserDetails;
import com.company.taskquiz.entity.dto.request.AddToUserScoreRequest;
import com.company.taskquiz.entity.dto.request.UpdateUserRequest;
import com.company.taskquiz.entity.dto.request.UserRequest;
import com.company.taskquiz.entity.dto.response.UserResponse;
import com.company.taskquiz.exceptions.TaskQuizExceptions;
import com.company.taskquiz.repository.RolesRepository;
import com.company.taskquiz.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService implements UserOperations, UserDetailsService {

    private final UserRepository userRepository;

    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserResponse> findById(Long id) {
        return userRepository.findById(id).map(UserResponse::fromUser);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(UserResponse::fromUser).collect(Collectors.toList());
    }

    @Override
    public UserResponse create(UserRequest request) {
        return UserResponse.fromUser(save(request, getRegularUserAuthorities()));
    }

    @Override
    public UserResponse createAdmin(UserRequest request) {
        return UserResponse.fromUser(save(request, getAdminAuthorities()));
    }

    @Override
    public UserResponse update(UpdateUserRequest request) {
        if(!userRepository.existsById(request.getId())){
            throw TaskQuizExceptions.userNotFound(request.getId());
        }

        return UserResponse.fromUser(merge(request));
    }

    @Override
    public void delete(Long id) {
        if(!userRepository.existsById(id)){
            throw TaskQuizExceptions.userNotFound(id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getLeaders(Long num) {
        List<QuizUser> leaders = userRepository.findLeaders();
        return leaders.subList(0, Math.min(num.intValue(), leaders.size())).
                stream().
                map(UserResponse::fromUser).collect(Collectors.toList());
    }

    @Override
    public UserResponse addToScore(AddToUserScoreRequest request) {
        if(!userRepository.existsById(request.getId())){
            throw TaskQuizExceptions.userNotFound(request.getId());
        }

        QuizUser user = userRepository.getById(request.getId());

        user.setScore(user.getScore() + request.getValue());

        userRepository.save(user);

        return UserResponse.fromUser(user);
    }

    private QuizUser save(UserRequest request, Map<Role, UserRole> roles){
        if(userRepository.existsByLogin(request.getLogin())){
            throw TaskQuizExceptions.duplicateLogin(request.getLogin());
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw TaskQuizExceptions.duplicateEmail(request.getEmail());
        }

        QuizUser quizUser = new QuizUser();

        quizUser.setLogin(request.getLogin());
        quizUser.setActive(true);
        quizUser.setCreatedAt(OffsetDateTime.now());
        quizUser.setEmail(request.getEmail());
        quizUser.setPassword(passwordEncoder.encode(request.getPassword()));

        quizUser.setRoles(roles);

        userRepository.save(quizUser);

        return quizUser;
    }

    private QuizUser merge(UpdateUserRequest request){
        QuizUser user = userRepository.getById(request.getId());

        user.setLogin(request.getLogin());
        user.setActive(true);
        user.setCreatedAt(request.getCreatedAt());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QuizUser user = userRepository.findByLogin(s).
                orElseThrow(() -> new UsernameNotFoundException("User with login " + " not found"));

        return new UserDetails(user);
    }

    private Map<Role, UserRole> getAdminAuthorities() {
        return rolesRepository.findAllByIdIn(rolesRepository.ADMIN_AUTHORITIES)
                .collect(Collectors.toMap(
                        UserRole::getId,
                        Function.identity(),
                        (e1, e2) -> e2,
                        () -> new EnumMap<>(Role.class)));
    }

    private Map<Role, UserRole> getRegularUserAuthorities() {
        UserRole authority = rolesRepository
                .findById(Role.USER)
                .orElseThrow(() -> TaskQuizExceptions.authorityNotFound(Role.USER.name()));

        Map<Role, UserRole> authorities = new EnumMap<>(Role.class);
        authorities.put(Role.USER, authority);
        return authorities;
    }

}
