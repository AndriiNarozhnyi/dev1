package com.training.springproject.repository;

import com.training.springproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


    @Query (value ="select u.id,u.username,u.username_ukr,u.email,u.password,u.active " +
            "from user u inner join user_role ur on u.id = ur.user_id where ur.roles='TEACHER' and u.active=1"
            , nativeQuery = true)
    List<User> findByRoleTeacher();

    Optional<User> findByIdAndActiveTrue(Long id);
}
