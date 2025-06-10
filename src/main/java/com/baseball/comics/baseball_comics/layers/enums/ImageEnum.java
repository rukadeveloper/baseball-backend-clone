package com.baseball.comics.baseball_comics.layers.enums;

public enum ImageEnum {
    KIA("/images/kbo/kia.svg"),
    NC("/images/kbo/nc.svg"),
    SSG("/images/kbo/ssg.svg"),
    DOOSAN("/images/kbo/doosan.png"),
    HANWHA("/images/kbo/hanhwa.svg"),
    SAMSUNG("/images/kbo/samsung.svg"),
    KT("/images/kbo/kt.svg"),
    LOTTE("/images/kbo/lotte.svg"),
    KIWOOM("/images/kbo/kiwoom.svg"),
    LG("/images/kbo/twins.svg");

    private final String value;

    ImageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}