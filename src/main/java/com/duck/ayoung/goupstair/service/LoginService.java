package com.duck.ayoung.goupstair.service;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     *
     * @return null 로그인 실패
     */
    public Member login(String loginId, String password) {
        List<Member> member = memberRepository.findByLogin(loginId, password);
        if(member.isEmpty()) {
            return null;
        } else {
            return member.get(0);
        }
    }
}
