package com.mysite.shoppingMall.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderSheetForm {
    private String orderSheetNumber;
    @NotNull(message = "주문자 이름을 입력하세요.")
    private String orderSheetName;
    private String orderSheetCellPhone1;
    private String orderSheetCellPhone2;
    private String orderSheetCellPhone3;
    private String orderSheetEmail1;
    private String orderSheetEmail2;
    @NotNull(message = "수령인을 입력하세요.")
    private String orderSheetReceiver;
    private String orderSheetReceiverPhone1;
    private String orderSheetReceiverPhone2;
    private String orderSheetReceiverPhone3;
    private String orderSheetReceiverAddress1;
    private String orderSheetReceiverAddress2;
    private String orderSheetReceiverAddress3;
    private String orderSheetReceiverAddress4;
    private String orderSheetReceiverOption;
    @NotNull(message = "결제가 필요합니다.")
    private String paymentSuccess;
}
