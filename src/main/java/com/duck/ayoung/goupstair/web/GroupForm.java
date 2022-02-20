package com.duck.ayoung.goupstair.web;

import lombok.Getter;

@Getter
public class GroupForm {

    private String name;
    private String iconImage;
    private String description;

    public GroupForm(String name, String iconImage, String description) {
        this.name = name;
        this.iconImage = iconImage;
        this.description = description;
    }
}
