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
        MemberForm memberForm1 = new MemberForm("test1", "tester1", "test1");
        MemberForm memberForm2 = new MemberForm("test2", "tester2", "test2");
        GroupForm groupForm = new GroupForm("group1", null, null);
        Long memberId1 = memberService.join(memberForm1);
        Long memberId2 = memberService.join(memberForm2);
        Long groupId = groupService.createGroup(groupForm, memberId1);

        //when
        groupService.joinGroup(groupId, memberId2);
        List<Member> member = groupService.findMember(groupId);

        //then
        assertThat(member).contains(memberRepository.findOne(memberId1), memberRepository.findOne(memberId2));

    }

    @Test
    public void 그룹_조회() {
        //given
        MemberForm memberForm1 = new MemberForm("test1", "tester1", "test1");
        GroupForm groupForm1 = new GroupForm("group1", null, null);
        GroupForm groupForm2 = new GroupForm("group2", null, null);
        Long memberId1 = memberService.join(memberForm1);
        Long groupId1 = groupService.createGroup(groupForm1, memberId1);
        Long groupId2 = groupService.createGroup(groupForm2, memberId1);

        //when
        List<Group> groups = groupService.findGroupsByMember(memberRepository.findOne(memberId1));

        //then
        assertThat(groups).contains(groupRepository.findOne(groupId1), groupRepository.findOne(groupId2));
    }

    @Test
    public void 그룹_없을때_조회() {
        //given
        MemberForm memberForm1 = new MemberForm("test1", "tester1", "test1");
        MemberForm memberForm2 = new MemberForm("test2", "tester2", "test2");
        GroupForm groupForm1 = new GroupForm("group1", null, null);
        GroupForm groupForm2 = new GroupForm("group2", null, null);
        Long memberId1 = memberService.join(memberForm1);
        Long memberId2 = memberService.join(memberForm2);
        Long groupId1 = groupService.createGroup(groupForm1, memberId1);
        Long groupId2 = groupService.createGroup(groupForm2, memberId1);

        //when
        List<Group> groups = groupService.findGroupsByMember(memberRepository.findOne(memberId2));

        //then
        assertThat(groups.size()).isEqualTo(0);
    }

}