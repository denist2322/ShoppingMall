package com.mysite.shoppingMall.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cartColor;
    private String cartSize;
    private long cartCount;
    @ManyToOne
    private Product product;
    @ManyToOne
    private MallUser mallUser;
}
