package com.duck.ayoung.goupstair.web.login;

import com.duck.ayoung.goupstair.service.LoginService;
import com.duck.ayoung.goupstair.service.MemberService;
import com.duck.ayoung.goupstair.web.member.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping
    public String login(@ModelAttribute LoginForm loginForm) {
        loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        return "redirect:/";
    }
}
