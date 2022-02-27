package com.duck.ayoung.goupstair.web.login;

import lombok.Getter;

@Getter
public class LoginForm {

    private String loginId;
    private String password;

    public LoginForm(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
