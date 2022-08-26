package com.mysite.shoppingMall.Domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
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

    @ManyToOne
    MallUser mallUser;
}
