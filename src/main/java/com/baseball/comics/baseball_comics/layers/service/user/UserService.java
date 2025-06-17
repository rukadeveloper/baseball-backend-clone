package com.baseball.comics.baseball_comics.layers.service.user;

import com.baseball.comics.baseball_comics.layers.dto.join.JoinDTO;
import com.baseball.comics.baseball_comics.layers.dto.join.JoinResponseDTO;
import com.baseball.comics.baseball_comics.layers.repository.User.UserEntity;
import com.baseball.comics.baseball_comics.layers.repository.User.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinResponseDTO joinProcess(JoinDTO joinDTO) {
        String uid = joinDTO.getUserId();
        String pwd = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        String name = joinDTO.getName();
        String phone = joinDTO.getPhone();

        Boolean isExist = userJpaRepository.existsByUid(uid);

        if(isExist) { return new JoinResponseDTO(false, null); }

        UserEntity userEntity = new UserEntity();

        userEntity.setUid(uid);
        userEntity.setPassword(bCryptPasswordEncoder.encode(pwd));
        userEntity.setRole("ROLE_ADMIN");
        userEntity.setUemail(email);
        userEntity.setUname(name);
        userEntity.setUphone(phone);

        userJpaRepository.save(userEntity);

        return new JoinResponseDTO(true, userEntity);
    }

    public Boolean nestCheck(String uid) {
        Boolean isExist = userJpaRepository.existsByUid(uid);
        if(isExist) {
            return false;
        }
        return true;
    }
}
