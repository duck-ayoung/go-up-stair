package com.duck.ayoung.goupstair.repository;

import com.duck.ayoung.goupstair.domain.Member;
import com.duck.ayoung.goupstair.domain.Stair;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StairRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(int value, Member member) {
        Stair stair = new Stair(value, member);
        em.persist(stair);
    }
}
