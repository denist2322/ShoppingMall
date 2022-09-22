package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
// == 질문 작성 관련 DTO ==
public class QuestionForm {
    @NotEmpty(message = "제목은 필수항목 입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용은 필수항목 입니다.")
    private String content;
}
