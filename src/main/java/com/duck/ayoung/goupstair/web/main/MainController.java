package com.duck.ayoung.goupstair.web.main;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.service.MemberService;
import com.duck.ayoung.goupstair.service.StairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final StairService stairService;
    private final MemberService memberService;

    @GetMapping
    public String main(Model model) {
        //Todo member find by session
        List<Member> test1 = memberService.findByNickNameContaining("test1");
        model.addAttribute("totalStairValue", stairService.getSumStairValueForWeek(test1.get(0).getId()));
        return "main/main";
    }

    @PostMapping("/stair/add")
    public String addStair(Integer stairValue, Model model) {
        log.info("stair {}", stairValue);
        //Todo member find by session
        List<Member> test1 = memberService.findByNickNameContaining("test1");
        stairService.save(stairValue, test1.get(0));
        model.addAttribute("totalStairValue", stairService.getSumStairValueForWeek(test1.get(0).getId()));
        return "main/main";
    }
}
