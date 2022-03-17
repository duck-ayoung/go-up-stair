package com.duck.ayoung.goupstair.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class InvitationNotification extends Notification {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public InvitationNotification() {
    }

    public InvitationNotification(String content, Member member, Group group) {
        super(content, member);
        this.group = group;
    }
}
