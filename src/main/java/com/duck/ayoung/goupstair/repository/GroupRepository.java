package com.duck.ayoung.goupstair.repository;

import com.duck.ayoung.goupstair.common.DateUtil;
import com.duck.ayoung.goupstair.domain.Group;
import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.MemberGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class GroupRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Group group, Member member) {
        em.persist(group);
        MemberGroup memberGroup = new MemberGroup(member, group);
        em.persist(memberGroup);
    }

    public void join(Group group, Member member) {
        MemberGroup memberGroup = new MemberGroup(member, group);
        em.persist(memberGroup);
    }

    public Group findOne(Long groupId) {
        return em.find(Group.class, groupId);
    }

    public List<Member> findMember(Long groupId) {
        return em.createQuery("select mg.member from MemberGroup mg " +
                "join mg.group g on g.id= :groupId", Member.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

    public List<Group> findGroupsByMember(Member member) {
        return em.createQuery("select mg.group from MemberGroup mg where mg.member.id = :memberId", Group.class)
                .setParameter("memberId", member.getId())
                .getResultList();
    }

    public List<RankInfo> findRankInfo(Long groupId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate thisMonday = DateUtil.getThisMonday(LocalDate.from(localDateTime));

        List<RankInfo> resultList = em.createQuery("select new com.duck.ayoung.goupstair.repository.RankInfo(s.member, sum(s" +
                ".stairValue) as stairValue) " +
                "from Stair s " +
                "join s.member " +
                "where s.createDateTime >= :thisMonday " +
                "and s.createDateTime <= :localDate " +
                "and s.member in (select mg.member from MemberGroup mg where mg.group.id = :groupId) " +
                "group by s.member " +
                "order by stairValue desc", RankInfo.class)
                .setParameter("thisMonday", thisMonday.atTime(0, 0))
                .setParameter("localDate", localDateTime)
                .setParameter("groupId", groupId)
                .setMaxResults(3)
                .getResultList();


        return resultList;
    }

}
