package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBuyForm {
    private long productId;
    private String orderImage;
    private String orderTitle;
    private String orderColor;
    private String orderSize;
    private String orderPrice;
    private long orderCounter;
    private long orderNumber;
    private String orderEmail1;
    private String orderEmail2;
    private String orderCellPhone1;
    private String orderCellPhone2;
    private String orderCellPhone3;
    private String orderAddress1;
    private String orderAddress2;
    private String orderAddress3;
    private String orderAddress4;
}
