package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FindEmailForm {
    @NotNull(message = "이름을 입력해주세요.")
    private String fNname;
    @NotNull(message = "생년월일 6자리를 입력해주세요.")
    private Integer fNbirthday;
}
