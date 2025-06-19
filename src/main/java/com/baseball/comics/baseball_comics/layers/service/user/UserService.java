package com.baseball.comics.baseball_comics.layers.service.user;

import com.baseball.comics.baseball_comics.layers.dto.join.FindIdDTO;
import com.baseball.comics.baseball_comics.layers.dto.join.JoinDTO;
import com.baseball.comics.baseball_comics.layers.dto.join.JoinResponseDTO;
import com.baseball.comics.baseball_comics.layers.dto.join.UpdateDTO;
import com.baseball.comics.baseball_comics.layers.repository.User.UserEntity;
import com.baseball.comics.baseball_comics.layers.repository.User.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userServiceV1")
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
        String profile = joinDTO.getProfile();


        Boolean isExist = userJpaRepository.existsByUid(uid);

        if(isExist) { return new JoinResponseDTO(false, null); }

        UserEntity userEntity = new UserEntity();

        userEntity.setUid(uid);
        userEntity.setPassword(bCryptPasswordEncoder.encode(pwd));
        userEntity.setRole("ROLE_USER");
        userEntity.setUemail(email);
        userEntity.setUname(name);
        userEntity.setUphone(phone);
        userEntity.setProfile(profile);

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

    public void updateInfo(UpdateDTO updateDTO) {
        UserEntity userEntity = userJpaRepository.findByUid(updateDTO.getUserId());
        userEntity.setPassword(bCryptPasswordEncoder.encode(updateDTO.getPassword()));
        userEntity.setUname(updateDTO.getName());
        userEntity.setUemail(updateDTO.getEmail());

        userJpaRepository.save(userEntity);
    }

    public List<UserEntity> findId(FindIdDTO findIdDTO) {
        List<UserEntity> array = new ArrayList<>();
        List<UserEntity> list = userJpaRepository.findByUname(findIdDTO.getName());
        for(UserEntity entity: list ) {
            array.add(entity);
        }
        if (list == null) {
            // 예외 처리 또는 에러 메시지 반환
             return null;
        } else {
             return list;
        }
    }
}
