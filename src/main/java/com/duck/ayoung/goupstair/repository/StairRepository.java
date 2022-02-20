package com.duck.ayoung.goupstair.repository;

import com.duck.ayoung.goupstair.common.DateUtil;
import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.Stair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StairRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(int value, Member member) {
        Stair stair = new Stair(value, member);
        em.persist(stair);
    }

    public void saveForTest(int value, Member member, LocalDateTime localDateTime) {
        Stair stair = new Stair(value, member, localDateTime);
        em.persist(stair);
    }

    public Long getSumStairValueForWeek(Long memberId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate thisMonday = DateUtil.getThisMonday(LocalDate.from(localDateTime));

        List<Long> resultList = em.createQuery("select sum(s.stairValue) from Stair s " +
                                "join Member m on m.id = :memberId " +
                                "where s.createDateTime >= :thisMonday " +
                                "and s.createDateTime <= :localDate", Long.class)
                        .setParameter("memberId", memberId)
                        .setParameter("thisMonday", thisMonday.atTime(0,0))
                        .setParameter("localDate", localDateTime)
                        .getResultList();

        if (resultList.get(0) == null) {
            return 0L;
        } else {
            return resultList.get(0);
        }
    }
}
