package com.duck.ayoung.goupstair.web.group;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.service.GroupService;
import com.duck.ayoung.goupstair.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/add")
    public String save(@Login Member loginMember, @ModelAttribute GroupForm groupForm) {
        if (loginMember == null) {
            return "redirect:/";
        }

        log.info("groupForm.getName {}", groupForm.getName());
        groupService.createGroup(groupForm, loginMember.getId());

        return "redirect:/main";
    }
}
