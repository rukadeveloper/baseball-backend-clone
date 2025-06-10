package com.baseball.comics.baseball_comics.layers.enums.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonError {

    CRAWLING_ERROR("웹 크롤링 중 오류가 발생했습니다."),
    NO_TEAM_DATA("팀 데이터가 존재하지 않습니다."),

    JOIN_ERROR("회원가입 중 에러가 발생했습니다"),
    PASSWORD_NOT_ILLGAL("패스워드 불일치"),
    LOGIN_ERROR("요청하신 아이디가 없습니다");
    private final String message;
}
