package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
// == 비밀번호 찾기에 관련된 DTO ==
public class FindPwForm {
    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    private String authentication;
    private String confirmAuthentication;
    private String isSuccess;
    private String isSend;

}
