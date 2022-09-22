package com.mysite.shoppingMall.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
// == 제품 사이즈 테이블 ==
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productSize;
    // 제품 사이즈 테이블을 제품 테이블에 연결시킨다.
    @ManyToOne
    private Product product;
}
