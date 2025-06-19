package com.baseball.comics.baseball_comics.layers.dto.join;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDTO {
    private String userId;
    private String password;
    private String email;
    private String name;
}
