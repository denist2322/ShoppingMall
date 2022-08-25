package com.mysite.shoppingMall.Domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class OrderSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String sheetOrderNumber;
    public String sheetOrderEmail;
    public String sheetOrderName;
    public String sheetOrderPhone;
    public String sheetReceiverName;
    public String sheetReceiverPhone;
    public String sheetReceiverAddress;
    public String sheetOption;
    public String shippingCost;
    public String productCost;
    public String totalPrice;
    public int nowState;
}
