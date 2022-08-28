package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBuyForm {
    private long productsId;
    private String orderImage;
    private String orderTitle;
    private String orderColor;
    private String orderSize;
    private String orderPrice;
    private long orderCounter;
    private long orderNumber;
    private long orderTotalPrice;
    private long shippingCost;
}
