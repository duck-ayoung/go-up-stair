package com.duck.ayoung.goupstair.service;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.Stair;
import com.duck.ayoung.goupstair.repository.MemberRepository;
import com.duck.ayoung.goupstair.repository.StairRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class StairServiceTest {

    @Autowired StairService stairService;
    @Autowired StairRepository stairRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 계단수_저장() {
        //given
        int value = 10;
        Member member = new Member("test1", "tester1", "test1");
        memberRepository.save(member);
        Long preSaveSumStairValue = stairRepository.getSumStairValueForWeek(member.getId());
        Member member2 = new Member("test2", "tester2", "test2");
        memberRepository.save(member2);

        //when
        stairService.save(value, member);
        stairService.save(value, member2);
        Long postSaveSumStairValue = stairRepository.getSumStairValueForWeek(member.getId());

        //then
        assertThat(postSaveSumStairValue).isEqualTo(value+preSaveSumStairValue);


    }

    @Test
    public void 이번주_계단수_총합() {
        //given
        Member member = new Member("test1", "tester1", "test1");
        memberRepository.save(member);

        int oldValue = 10;
        LocalDateTime oldDateTime = LocalDateTime.of(2022, 2, 13, 1, 3);
        stairRepository.saveForTest(oldValue, member, oldDateTime);

        int thisWeekValue1 = 10;
        stairRepository.saveForTest(thisWeekValue1, member, LocalDateTime.of(2022, 2, 19, 1, 3));

        int thisWeekValue2 = 20;
        stairRepository.save(thisWeekValue2, member);

        //when
        Long sumStairValueForWeek = stairRepository.getSumStairValueForWeek(member.getId());

        //then
        assertThat(sumStairValueForWeek).isEqualTo(thisWeekValue1 + thisWeekValue2);
    }
}