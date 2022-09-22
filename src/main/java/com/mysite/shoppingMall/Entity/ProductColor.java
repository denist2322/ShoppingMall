package com.mysite.shoppingMall.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
// == 제품 색상 테이블 ==
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productColor;
    // 제품 색상 테이블을 제품에 연결 시킨다.
    @ManyToOne
    private Product product;
}
