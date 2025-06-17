package com.baseball.comics.baseball_comics.layers.controller.user;

import com.baseball.comics.baseball_comics.layers.dto.common.ApiResponseDTO;
import com.baseball.comics.baseball_comics.layers.dto.common.MessageType;
import com.baseball.comics.baseball_comics.layers.dto.join.*;
import com.baseball.comics.baseball_comics.layers.repository.User.UserEntity;
import com.baseball.comics.baseball_comics.layers.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/join")
    public ApiResponseDTO<UserEntity> joinProcess(JoinDTO joinDTO) {
        JoinResponseDTO response = userService.joinProcess(joinDTO);
        if(response.isExist()) {
            return ApiResponseDTO.success(MessageType.CREATE, response.getUserEntity());
        } else {
            return ApiResponseDTO.fail(MessageType.FAIL);
        }
    }

    @GetMapping("/login/data")
    public ApiResponseDTO<LoginResponseDTO> getLoginData() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return ApiResponseDTO.success(MessageType.RETRIEVE, new LoginResponseDTO(username, role));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/nest/check/id")
    public ApiResponseDTO<NestCheckResponseDTO> checkIdNested(NestCheckDTO nestCheckDTO) {
        Boolean isExisted = userService.nestCheck(nestCheckDTO.getUid());

        return ApiResponseDTO.success(MessageType.RETRIEVE, new NestCheckResponseDTO(isExisted));
    }
}
