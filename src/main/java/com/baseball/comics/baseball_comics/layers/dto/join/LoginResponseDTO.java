package com.baseball.comics.baseball_comics.layers.dto.join;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String uid;
    private String uname;
    private String uemail;
    private String role;
}
