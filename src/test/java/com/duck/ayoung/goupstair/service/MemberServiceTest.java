package com.duck.ayoung.goupstair.service;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.Stair;
import com.duck.ayoung.goupstair.repository.MemberRepository;
import com.duck.ayoung.goupstair.web.MemberForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() {
        //given
        MemberForm memberForm = new MemberForm("test1", "tester1", "test1");

        //when
        Long memberId = memberService.join(memberForm);
        Member findMember = memberRepository.findOne(memberId);

        //then
        assertThat(memberForm.getLoginId()).isEqualTo(findMember.getLoginId());
        assertThat(memberForm.getPassword()).isEqualTo(findMember.getPassword());
        assertThat(memberForm.getNickName()).isEqualTo(findMember.getNickName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        MemberForm memberForm1 = new MemberForm("test1", "tester1", "test1");
        MemberForm memberForm2 = new MemberForm("test1", "tester2", "test2");

        //when
        memberService.join(memberForm1);

        //then
        assertThrows(IllegalStateException.class,
                ()-> memberService.join(memberForm2));
    }

    @Test
    public void 로그인_아이디_검색() {
        //given
        MemberForm memberForm1 = new MemberForm("test1", "tester1", "test1");
        MemberForm memberForm2 = new MemberForm("2test", "tester2", "test2");
        MemberForm memberForm3 = new MemberForm("3", "tester3", "test3");

        memberService.join(memberForm1);
        memberService.join(memberForm2);
        memberService.join(memberForm3);

        //when
        List<Member> idTest = memberService.findByLoginIdContaining("test");
        List<Member> idTest1 = memberService.findByLoginIdContaining("test1");

        //then
        assertThat(idTest.size()).isEqualTo(2);
        assertThat(idTest1.size()).isEqualTo(1);
    }

    @Test
    public void 로그인_닉네임_검색() {
        //given
        MemberForm memberForm1 = new MemberForm("test1", "tester1", "test1");
        MemberForm memberForm2 = new MemberForm("test2", "2tester", "test2");
        MemberForm memberForm3 = new MemberForm("test3", "3", "test3");

        memberService.join(memberForm1);
        memberService.join(memberForm2);
        memberService.join(memberForm3);

        //when
        List<Member> idTester = memberService.findByNickNameContaining("tester");
        List<Member> idTester1 = memberService.findByNickNameContaining("tester1");

        //then
        assertThat(idTester.size()).isEqualTo(2);
        assertThat(idTester1.size()).isEqualTo(1);
    }

    @Test
    public void 계단() {
        MemberForm memberForm1 = new MemberForm("test1", "tester1", "test1");
        Long join = memberService.join(memberForm1);
        Member one = memberRepository.findOne(join);

        Stair stair = new Stair(10, one);
        Stair stair2 = new Stair(20, one);
        em.persist(stair);
        em.persist(stair2);
        Long stair1 = memberRepository.getSumStairValue(join);
        System.out.println("stair1 = " + stair1);
    }
}