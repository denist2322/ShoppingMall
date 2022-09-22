package com.mysite.shoppingMall.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
// == 쇼핑카트 테이블 ==
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cartColor;
    private String cartSize;
    private long cartCount;
    private long cartTotalPrice;
    private int checked;
    // 쇼핑카트 테이블에 제품 테이블을 연결 시킨다. (쇼핑카트 조회시 제품의 정보를 알아내기 위해서 이다.)
    @ManyToOne
    private Product product;
    // 쇼핑카트 테이블에 유저 테이블을 연결 시킨다. (쇼핑카트 조회시 유저 정보를 알아 내기 위해서이다.)
    @ManyToOne
    private MallUser mallUser;
}
