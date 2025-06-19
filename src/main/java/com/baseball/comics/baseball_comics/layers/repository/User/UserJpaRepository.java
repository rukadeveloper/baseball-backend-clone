package com.baseball.comics.baseball_comics.layers.repository.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUid(String uid);

    UserEntity findByUid(String username);

    UserEntity findByName(String name);
}
