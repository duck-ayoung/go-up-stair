package com.duck.ayoung.goupstair.service;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.repository.MemberRepository;
import com.duck.ayoung.goupstair.web.member.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberForm memberForm) {
        validateDuplicateMember(memberForm);
        Member member = new Member(memberForm.getLoginId(), memberForm.getNickName(), memberForm.getPassword());
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(MemberForm memberForm) {
        List<Member> members = memberRepository.findByLoginId(memberForm.getLoginId());
        if(!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findByLoginIdContaining(String loginId) {
        return memberRepository.findByLoginIdContaining(loginId);
    }

    public List<Member> findByNickNameContaining(String nickName) {
        return memberRepository.findByNickNameContaining(nickName);
    }



}
