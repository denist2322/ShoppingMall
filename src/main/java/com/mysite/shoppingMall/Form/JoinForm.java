package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JoinForm {
    @NotNull
    private String email2;
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password1;
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password2;
    @NotNull(message = "이름을 입력해주세요.")
    private String name2;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    @NotNull(message = "생년월일을 입력해주세요.")
    private Integer birthday2;
    @NotNull(message = "핸드폰 번호를 입력해주세요.")
    private String cellphone2;
}
