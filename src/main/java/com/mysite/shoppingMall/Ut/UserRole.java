package com.mysite.shoppingMall.Ut;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("7"),
    USER("3");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
