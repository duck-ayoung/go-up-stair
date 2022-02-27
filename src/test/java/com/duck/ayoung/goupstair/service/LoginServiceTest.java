package com.duck.ayoung.goupstair.service;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.repository.MemberRepository;
import com.duck.ayoung.goupstair.web.member.MemberForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    LoginService loginService;

    @Test
    public void 로그인() {
        //given
        MemberForm memberForm1 = new MemberForm("test1", "tester1", "test1");
        Long memberId = memberService.join(memberForm1);

        //when
        Member rightPasswordMember = loginService.login(memberForm1.getLoginId(), memberForm1.getPassword());
        Member wrongPasswordMember = loginService.login(memberForm1.getLoginId(), "1");
        Member findMember = memberRepository.findOne(memberId);

        //then
        assertThat(rightPasswordMember).isEqualTo(findMember);
        assertThat(wrongPasswordMember).isNull();
    }
}