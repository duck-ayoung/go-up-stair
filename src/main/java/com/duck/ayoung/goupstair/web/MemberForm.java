package com.duck.ayoung.goupstair.web;

import lombok.Getter;

@Getter
public class MemberForm {

    private String loginId;
    private String nickName;
    private String password;

    public MemberForm(String loginId, String nickName, String password) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.password = password;
    }
}
