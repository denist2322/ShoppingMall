package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JoinForm {
    @NotEmpty(message = "이메일을 입력하세요.")
    private String email2;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password1;
    @NotEmpty(message = "비밀번호를 한번 더 입력해주세요.")
    private String password2;
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name2;
    @NotEmpty(message = "우편번호를 입력해주세요.")
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    @NotNull(message = "생년월일을 입력해주세요.")
    private Integer birthday2;
    @NotEmpty(message = "핸드폰 번호를 입력해주세요.")
    private String cellphone2_1;
    @NotEmpty(message = "핸드폰 번호를 입력해주세요.")
    private String cellphone2_2;
    @NotEmpty(message = "핸드폰 번호를 입력해주세요.")
    private String cellphone2_3;
}
