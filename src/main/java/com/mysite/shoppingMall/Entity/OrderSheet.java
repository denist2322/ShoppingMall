package com.mysite.shoppingMall.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
// == 주문서 정보 테이블 ==
public class OrderSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public LocalDateTime regDate;
    public String sheetNumber;
    public String sheetOrdererEmail;
    public String sheetOrdererName;
    public String sheetOrdererPhone;
    public String sheetReceiverName;
    public String sheetReceiverPhone;
    public String sheetReceiverAddress;
    public String sheetOption;
    public long shippingCost;
    public long productCost;
    public long totalPrice;
    public int nowState;
    public String sheetProductColor;
    public String sheetProductSize;
    public long sheetProductCount;

    @ManyToOne
    MallUser mallUser;

    @ManyToOne
    Product product;
}
