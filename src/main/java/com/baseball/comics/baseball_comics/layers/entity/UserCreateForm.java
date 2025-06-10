package com.baseball.comics.baseball_comics.layers.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateForm {

    @NotEmpty
    private String userName;

    @Email
    private String email;

    @NotEmpty
    private String password1;

    @NotEmpty
    private String password2;
}
