package com.duck.ayoung.goupstair.web.member;

import com.duck.ayoung.goupstair.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute MemberForm memberForm) {
        return "members/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute MemberForm memberForm) {
        memberService.join(memberForm);
        return "redirect:/login";
    }
}
