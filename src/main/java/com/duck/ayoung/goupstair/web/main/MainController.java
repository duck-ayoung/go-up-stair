package com.duck.ayoung.goupstair.web.main;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.service.GroupService;
import com.duck.ayoung.goupstair.service.MemberService;
import com.duck.ayoung.goupstair.service.StairService;
import com.duck.ayoung.goupstair.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final StairService stairService;
    private final MemberService memberService;
    private final GroupService groupService;

    @GetMapping
    public String main(@Login Member loginMember,
                       Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("notification", memberService.findNotification(loginMember));
        model.addAttribute("totalStairValue",
                stairService.getSumStairValueForWeek(loginMember.getId()));
        model.addAttribute("groups", groupService.findGroupsByMember(loginMember));
        return "main/main";
    }

    @PostMapping("/stair/add")
    public String addStair(@Login Member loginMember,
                           Integer stairValue, Model model) {
        log.info("stair {}", stairValue);
        log.info("loginMember.getLoginId {}", loginMember.getLoginId());
        stairService.save(stairValue, loginMember);
        model.addAttribute("totalStairValue", stairService.getSumStairValueForWeek(loginMember.getId()));
        log.info("totalStairValue {}", stairService.getSumStairValueForWeek(loginMember.getId()));
        return "main/main";
    }
}
