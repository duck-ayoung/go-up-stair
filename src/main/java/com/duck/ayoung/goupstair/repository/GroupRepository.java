package com.duck.ayoung.goupstair.repository;

import com.duck.ayoung.goupstair.domain.Group;
import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.MemberGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

//    public List<Member> findRankMember(Long groupId) {
//        List<MemberGroup> memberGroups = em.createQuery("select mg from MemberGroup mg join mg.group g on g.id = :groupId", MemberGroup.class)
//                .setParameter("groupId", groupId)
//                .getResultList();
//
//        ArrayList<Member> list = new ArrayList<>();
//        for (MemberGroup memberGroup : memberGroups) {
//            list.add(memberGroup.getMember());
//        }
//
//        return list.stream().limit(3).collect(Collectors.toList());
//    }

}
