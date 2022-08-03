package com.mysite.shoppingMall.Vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MailDto {
    private String email;
    private String title;
    private String message;
    private String authentication;
    private String confirmAuthentication;
    private String success;
    private String fail;
    private String send;
}
