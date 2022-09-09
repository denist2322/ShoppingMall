package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSheetForm {
    private String orderSheetNumber;
    private String orderSheetName;
    private String orderSheetCellPhone1;
    private String orderSheetCellPhone2;
    private String orderSheetCellPhone3;
    private String orderSheetEmail1;
    private String orderSheetEmail2;
    private String orderSheetReceiver;
    private String orderSheetReceiverPhone1;
    private String orderSheetReceiverPhone2;
    private String orderSheetReceiverPhone3;
    private String orderSheetReceiverAddress1;
    private String orderSheetReceiverAddress2;
    private String orderSheetReceiverAddress3;
    private String orderSheetReceiverAddress4;
    private String orderSheetReceiverOption;

    private long orderSheetShippingCost;
    private long orderSheetProductCost;
    private long orderSheetTotalPrice;

    private String orderSheetColor;
    private String orderSheetSize;
    private Long orderSheetCount;
    private Long productsId;

    private String paymentSuccess;
}
