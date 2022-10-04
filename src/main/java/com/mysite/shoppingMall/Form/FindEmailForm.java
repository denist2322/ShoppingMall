package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
// == 이메일 찾기에 관련 DTO ==
public class FindEmailForm {
    @NotEmpty(message = "이름이 입력되지 않았습니다.")
    private String fNname;
    @NotNull(message = "생년월일이 입력되지 않았습니다.")
    private Integer fNbirthday;
}
