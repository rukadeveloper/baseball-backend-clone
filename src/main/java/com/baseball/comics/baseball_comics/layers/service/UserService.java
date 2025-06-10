package com.baseball.comics.baseball_comics.layers.service;

import com.baseball.comics.baseball_comics.layers.Repository.UserRepository;
import com.baseball.comics.baseball_comics.layers.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User get(String userName) {
        User user =  userRepository.findByUserName(userName).orElseThrow(() -> new IllegalArgumentException(userName));
        return user;
    }


    public void create(String userName, String email, String password) {
        User user = new User(email, password);
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
    }

}
