package com.duck.ayoung.goupstair.repository;

import com.duck.ayoung.goupstair.domain.Member;
import lombok.Getter;

@Getter
public class RankInfo {

    Member member;
    Long stairValue;

    public RankInfo(Member member, Long stairValue) {
        this.member = member;
        this.stairValue = stairValue;
    }
}
