package com.mysite.shoppingMall.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
// == 제품 테이블 ==
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
    // 제품에 제품색상 테이블을 연결 시킨다.
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductColor> productColorList;
    // 제품에 제품 사이즈를 연결 시킨다.
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductSize> productSizeList;
    // 제품에 제품 이미지를 연결 시킨다.
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductImage> productImageList;
}
