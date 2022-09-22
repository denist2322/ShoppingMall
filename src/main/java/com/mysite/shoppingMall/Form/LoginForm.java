package com.mysite.shoppingMall.Form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
// == 로그인 관련 DTO ==
public class LoginForm {
    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @JsonIgnore
    private String password;
}
