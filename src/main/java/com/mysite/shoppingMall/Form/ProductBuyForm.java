package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBuyForm {
    private String productId;
    private String orderImage;
    private String orderTitle;
    private String orderColor;
    private String orderSize;
    private String orderPrice;
    private int orderNumber;
    private String orderEmail1;
    private String orderEmail2;
}
