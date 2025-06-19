package com.baseball.comics.baseball_comics.layers.controller.user;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.baseball.comics.baseball_comics.layers.dto.common.ApiResponseDTO;
import com.baseball.comics.baseball_comics.layers.dto.common.MessageType;
import com.baseball.comics.baseball_comics.layers.dto.join.*;
import com.baseball.comics.baseball_comics.layers.dto.userDetails.CustomUserDetails;
import com.baseball.comics.baseball_comics.layers.repository.User.UserEntity;
import com.baseball.comics.baseball_comics.layers.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller("userControllerV1")
@ResponseBody
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/login/data")
    public ApiResponseDTO<LoginResponseDTO> getLoginData() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String uname = userDetails.getUname();
        String uemail = userDetails.getUemail();
        String password = userDetails.getPassword();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        System.out.println(username);
        System.out.println(role);
        System.out.println(uname);

        return ApiResponseDTO.success(MessageType.RETRIEVE, new LoginResponseDTO(username, password, uname, uemail, role));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/nest/check/id")
    public ApiResponseDTO<NestCheckResponseDTO> checkIdNested(NestCheckDTO nestCheckDTO) {
        Boolean isExisted = userService.nestCheck(nestCheckDTO.getUid());

        return ApiResponseDTO.success(MessageType.RETRIEVE, new NestCheckResponseDTO(isExisted));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/update")
    public ApiResponseDTO<String> updateInfo(UpdateDTO updateDTO) {
        userService.updateInfo(updateDTO);
        return ApiResponseDTO.success(MessageType.UPDATE, "업데이트 완료");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/find/id")
    public ApiResponseDTO<List<FindIdResponseDTO>> findId(FindIdDTO findIdDTO) {
        List<UserEntity> entities = userService.findId(findIdDTO);
        List<FindIdResponseDTO> lists = new ArrayList<>();
        if(entities == null) {
            lists.add(new FindIdResponseDTO("아이디가 없습니다"));
            return ApiResponseDTO.fail(MessageType.FAIL, lists);
        } else {
            List<FindIdResponseDTO> newEntities = entities.stream().map(v -> new FindIdResponseDTO(v.getUid())).collect(Collectors.toList());
            return ApiResponseDTO.success(MessageType.RETRIEVE, newEntities);
        }

    }

    @PostMapping("/upload")
    public ApiResponseDTO<ImgPathResponseDTO> upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            String fileUrl = "https://" + bucket + ".s3.amazonaws.com/" + fileName;
            return ApiResponseDTO.success(MessageType.CREATE, new ImgPathResponseDTO(fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponseDTO.fail(MessageType.FAIL, new ImgPathResponseDTO("업로드 실패"));
        }
    }

}
