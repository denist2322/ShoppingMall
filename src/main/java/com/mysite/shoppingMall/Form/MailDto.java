package com.mysite.shoppingMall.Form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
// == 메일 관련 DTO ==
public class MailDto {
    // 메일을 보내기 위한 정보를 저장한다.
    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    private String title;
    private String message;
    // 유저가 입력한 인증 번호를 저장함
    private String authentication;
    // 서버에서 인증번호를 가지고 있게 하기 위함
    private String confirmAuthentication;
    // 인증 성공 여부에 따라 success 혹은 fail
    private String success;
    private String fail;
}
