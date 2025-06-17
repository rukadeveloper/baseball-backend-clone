package com.baseball.comics.baseball_comics.layers.dto.join;

import com.baseball.comics.baseball_comics.layers.repository.User.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinResponseDTO {

    boolean isExist;
    UserEntity userEntity;
}
