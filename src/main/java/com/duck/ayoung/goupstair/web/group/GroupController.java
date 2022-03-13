package com.duck.ayoung.goupstair.web.group;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.SearchTypes;
import com.duck.ayoung.goupstair.repository.RankInfo;
import com.duck.ayoung.goupstair.service.GroupService;
import com.duck.ayoung.goupstair.service.MemberService;
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

    private final MemberService memberService;
    private final GroupService groupService;

    @ModelAttribute("searchTypes")
    public SearchTypes[] searchTypes() {
        return SearchTypes.values();
    }

    @GetMapping("/{groupId}")
    public String mainGroupView(@PathVariable Long groupId, Model model, @ModelAttribute String searchValue) {
        List<RankInfo> rankInfoTop3 = groupService.findRankInfoTop3(groupId);
        List<RankInfo> rankInfoAll = groupService.findRankInfoAll(groupId);
        model.addAttribute("rankInfoTop3", rankInfoTop3);
        model.addAttribute("rankInfoAll", rankInfoAll);
        return "groups/main";
    }

    @PostMapping("/{groupId}")
    public String search(@PathVariable Long groupId, Model model, @ModelAttribute String searchValue) {
        List<RankInfo> rankInfo = groupService.findRankInfoTop3(groupId);
        model.addAttribute("rankInfo", rankInfo);
        List<Member> members = memberService.findByLoginIdContaining(searchValue);
        model.addAttribute("members", members);
        return "groups/main";
    }

    @GetMapping("/{groupId}/invite/{memberId}")
    public String invite(@PathVariable Long groupId, @PathVariable Long memberId, Model model, @ModelAttribute String searchValue) {
        List<RankInfo> rankInfo = groupService.findRankInfoTop3(groupId);
        model.addAttribute("rankInfo", rankInfo);
        groupService.joinGroup(groupId, memberId);
        return "redirect:groups/main";
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
