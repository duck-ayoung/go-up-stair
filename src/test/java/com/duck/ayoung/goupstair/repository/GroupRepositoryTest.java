package com.duck.ayoung.goupstair.repository;

import com.duck.ayoung.goupstair.domain.Group;
import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.MemberGroup;
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

//    @Test
//    @Rollback(value = false)
//    public void 멤버랭크검색() {
//        Member member1 = new Member("test1", "tester1", "test1");
//        Member member2 = new Member("test2", "tester2", "test2");
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        Group group = new Group("group1", null, null);
//        groupRepository.save(group);
//        System.out.println("group.getId() = " + group.getId());
//
//        MemberGroup memberGroup = new MemberGroup(member1, group);
//        MemberGroup memberGroup2 = new MemberGroup(member2, group);
//        em.persist(memberGroup);
//        em.persist(memberGroup2);
//
//        List<Member> rankMember = groupRepository.findRankMember(group.getId());
//        for (Member memberGroup1 : rankMember) {
//            System.out.println("memberGroup.getGroup().getId() = " + memberGroup1.getNickName());
//        }
//
//    }

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