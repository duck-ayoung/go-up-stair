package com.duck.ayoung.goupstair.domain;

public enum SearchTypes {

    ID("아이디"), NICKNAME("닉네임");

    private final String description;

    SearchTypes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
