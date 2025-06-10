package com.baseball.comics.baseball_comics.layers.controller;

import com.baseball.comics.baseball_comics.layers.Exception.common.CommonException;
import com.baseball.comics.baseball_comics.layers.dto.common.ApiResponseDTO;
import com.baseball.comics.baseball_comics.layers.dto.common.MessageType;
import com.baseball.comics.baseball_comics.layers.entity.User;
import com.baseball.comics.baseball_comics.layers.entity.UserCreateForm;
import com.baseball.comics.baseball_comics.layers.enums.common.CommonError;
import com.baseball.comics.baseball_comics.layers.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/join")
    public String signUpForm(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/api/join")
    @CrossOrigin(origins = "http://localhost:3000")
    public ApiResponseDTO<User> signUp(@RequestBody @Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new CommonException(CommonError.JOIN_ERROR);
        }
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            throw new CommonException(CommonError.PASSWORD_NOT_ILLGAL);
        }
        userService.create(userCreateForm.getUserName(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        User result = userService.get(userCreateForm.getUserName());
        return ApiResponseDTO.success(MessageType.CREATE, result);
    }

}
