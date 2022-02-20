package com.duck.ayoung.goupstair.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberGroup {

    @Id @GeneratedValue
    @Column(name = "member_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;


    public MemberGroup(Member member, Group group) {
        this.member = member;
        this.group = group;
    }
}
