package com.duck.ayoung.goupstair.repository;

import com.duck.ayoung.goupstair.domain.Group;
import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.MemberGroup;
import com.duck.ayoung.goupstair.service.StairService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class GroupRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StairService stairService;
    @Autowired
    EntityManager em;

    @Test
    public void 멤버검색() {
        //given
        Member member1 = createMember("test1", "tester1", "test1");
        Member member2 = createMember("test2", "tester2", "test2");
        Member member3 = createMember("test3", "tester3", "test3");

        Group group1 = createGroup("group1", null, null, member1);
        Group group2 = createGroup("group2", null, null, member3);

        groupRepository.join(group1, member2);

        //when
        List<Member> members = groupRepository.findMember(group1.getId());

        //then
        assertThat(members).contains(member1, member2);

    }

    @Test
    public void 멤버랭크검색() {
        // given
        Member member1 = new Member("test1", "tester1", "test1");
        Member member2 = new Member("test2", "tester2", "test2");
        Member member3 = new Member("test3", "tester3", "test3");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Group group = new Group("group1", null, null);
        groupRepository.save(group, member1);
        groupRepository.join(group, member2);
        groupRepository.join(group, member3);

        stairService.save(20, member1);
        stairService.save(10, member2);
        stairService.save(30, member3);

        //when
        List<RankInfo> rankMember = groupRepository.findRankMember(group.getId());

        //then
        assertThat(rankMember.get(0).member).isEqualTo(member3);
        assertThat(rankMember.get(1).member).isEqualTo(member1);
        assertThat(rankMember.get(2).member).isEqualTo(member2);
    }

    public Member createMember(String loginId, String nickName, String password) {
        Member member = new Member(loginId, nickName, password);
        memberRepository.save(member);
        return member;
    }

    public Group createGroup(String name, String loginImage, String description, Member member) {
        Group group = new Group(name, loginImage, description);
        groupRepository.save(group, member);
        return group;
    }
}