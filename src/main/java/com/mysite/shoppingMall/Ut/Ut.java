package com.mysite.shoppingMall.Ut;

import javax.servlet.http.HttpSession;

public class Ut {

    public static boolean empty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String == false) {
            return true;
        }

        String str = (String) obj;

        return str.trim().length() == 0;
    }

    public static IsLogined isLogined(HttpSession session) {
        IsLogined result = new IsLogined();
        if (session.getAttribute("UserId") != null){
            result.setLogin(1);
            result.setUserId((int)session.getAttribute("UserId"));
            result.setAuthority((int)session.getAttribute("authority"));
            return result;
        }

        result.setLogin(0);
        result.setUserId(0);
        result.setAuthority(null);
        return result;
    }

    public static String[] splitEmail(String email){
        if(email.indexOf("@") == -1){
            return null;
        }

        String[] EmailTemp = email.split("\\@");

        return EmailTemp;
    }

    public static String[] splitAddress(String address){
        if(address.indexOf("**") == -1){
            return null;
        }

        String[] AddressTemp = address.split("\\*\\*");

        return AddressTemp;
    }

    public static String[] splitCellPhone(String phone){
        if(phone.indexOf("-") == -1){
            return null;
        }

        String[] phoneTemp = phone.split("\\-");

        return phoneTemp;
    }

    public static String[] splitStar(String color) {
        String[] AddressTemp = color.split("\\*\\*");

        return AddressTemp;
    }
}
