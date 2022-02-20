package com.duck.ayoung.goupstair.repository;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.Stair;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId =:loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    public List<Member> findByLoginIdContaining(String loginId) {
        return em.createQuery("select m from Member m where m.loginId like :loginId", Member.class)
                .setParameter("loginId", "%"+loginId+"%")
                .getResultList();
    }

    public List<Member> findByNickNameContaining(String nickName) {
        return em.createQuery("select m from Member m where m.nickName like :nickName", Member.class)
                .setParameter("nickName", "%"+nickName+"%")
                .getResultList();
    }

}
