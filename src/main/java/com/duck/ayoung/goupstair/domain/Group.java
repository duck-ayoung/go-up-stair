package com.duck.ayoung.goupstair.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "groups")
public class Group {

    @Id @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    @NotNull
    private String name;
    private String iconImage;
    private String description;

    public Group(String name, String iconImage, String description) {
        this.name = name;
        this.iconImage = iconImage;
        this.description = description;
    }
}
