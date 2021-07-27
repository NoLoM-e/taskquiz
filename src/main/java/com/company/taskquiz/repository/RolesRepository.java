package com.company.taskquiz.repository;

import com.company.taskquiz.entity.Role;
import com.company.taskquiz.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

public interface RolesRepository extends JpaRepository<UserRole, Role> {

    Set<Role> ADMIN_AUTHORITIES = EnumSet.of(Role.USER, Role.ADMIN);

    Stream<UserRole> findAllByIdIn(Set<Role> ids);

}
