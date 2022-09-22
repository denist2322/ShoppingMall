package com.mysite.shoppingMall.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
// == 제품 이미지 테이블 ==
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String images;
    // 제품 이미지 테이블을 제품 테이블에 연결 시킨다.
    @ManyToOne
    private Product product;
}
