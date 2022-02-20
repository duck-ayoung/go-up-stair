package com.duck.ayoung.goupstair.service;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.repository.StairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StairService {

    private final StairRepository stairRepository;

    @Transactional
    public void save(int value, Member member) {
        stairRepository.save(value, member);
    }

    public Long getSumStairValueForWeek(Long memberId) {
        return stairRepository.getSumStairValueForWeek(memberId);
    }
}
