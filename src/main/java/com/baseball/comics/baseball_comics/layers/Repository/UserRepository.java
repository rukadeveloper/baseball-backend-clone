package com.baseball.comics.baseball_comics.layers.Repository;

import com.baseball.comics.baseball_comics.layers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
}
