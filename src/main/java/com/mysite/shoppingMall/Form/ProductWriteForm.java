package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductWriteForm {
    private long id;
    @NotNull(message="제목을 입력하세요.")
    private String title;
    private String body;
    @NotNull(message="가격을 설정 해주세요.")
    private long price;
    private long discount;
    @NotNull(message="카테고리를 설정하세요.")
    private String category;
    @NotNull(message="색상을 입력해주세요.")
    private String color;
    @NotNull(message="사이즈를 입력해주세요.")
    private String size;
}
