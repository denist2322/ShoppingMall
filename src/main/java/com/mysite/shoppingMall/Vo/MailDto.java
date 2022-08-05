package com.mysite.shoppingMall.Vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class MailDto {
    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    private String title;
    private String message;
    private String authentication;
    private String confirmAuthentication;
    private String success;
    private String fail;
}
