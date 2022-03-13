package com.duck.ayoung.goupstair;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.web.argumentresolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(@Login Member loginMember, Model model) {
        model.addAttribute("loginMember", loginMember);
        return "home";
    }
}
