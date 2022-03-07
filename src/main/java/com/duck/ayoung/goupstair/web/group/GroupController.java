package com.duck.ayoung.goupstair.web.group;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.repository.RankInfo;
import com.duck.ayoung.goupstair.service.GroupService;
import com.duck.ayoung.goupstair.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public String mainGroupView(@PathVariable Long groupId, Model model) {
        List<RankInfo> rankInfo = groupService.findRankInfo(groupId);
        model.addAttribute("rankInfo", rankInfo);
        return "groups/main";
    }

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
