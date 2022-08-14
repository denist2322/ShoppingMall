package com.mysite.shoppingMall.Vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime regDate;
    private String title;
    private String body;
    private String mainImage;
    private long price;
    private long discount;
    private String category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductColor> productColorList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductSize> productSizeList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductImage> productImageList;
}
