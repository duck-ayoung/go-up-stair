package com.duck.ayoung.goupstair.service;

import com.duck.ayoung.goupstair.domain.Group;
import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.repository.GroupRepository;
import com.duck.ayoung.goupstair.repository.MemberRepository;
import com.duck.ayoung.goupstair.web.group.GroupForm;
import com.duck.ayoung.goupstair.web.member.MemberForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class GroupServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    GroupService groupService;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 그룹_생성() {
        //given
        MemberForm memberForm = new MemberForm("test1", "tester1", "test1");
        GroupForm groupForm = new GroupForm("group1", null, null);
        Long memberId = memberService.join(memberForm);

        //when
        Long groupId = groupService.createGroup(groupForm, memberId);
        Group group = groupRepository.findOne(groupId);

        //then
        assertThat(groupForm.getName()).isEqualTo(group.getName());
        assertThat(groupForm.getIconImage()).isEqualTo(group.getIconImage());
        assertThat(groupForm.getDescription()).isEqualTo(group.getDescription());

    }


    @Test
    public void 그룹_가입() {
        //given
        MemberForm memberForm = new MemberForm("test1", "tester1", "test1");
        GroupForm groupForm = new GroupForm("group1", null, null);
        Long memberId = memberService.join(memberForm);
        Long groupId = groupService.createGroup(groupForm, memberId);

        //when
        groupService.joinGroup(groupId, memberId);
        List<Member> member = groupService.findMember(groupId);

        //then
        assertThat(member).contains(memberRepository.findOne(memberId));

    }

}