package com.duck.ayoung.goupstair.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
public class Stair {

    @Id @GeneratedValue
    @Column(name = "stair_id")
    private Long id;

    private int stairValue;

    @NotNull
    private LocalDateTime createDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Stair() {
    }

    public Stair(int stairValue, Member member, LocalDateTime localDateTime) {
        this.stairValue = stairValue;
        this.createDateTime = localDateTime;
        this.member = member;
    }

    public Stair(int stairValue, Member member) {
        this.stairValue = stairValue;
        this.createDateTime = LocalDateTime.now();
        this.member = member;
    }
}
