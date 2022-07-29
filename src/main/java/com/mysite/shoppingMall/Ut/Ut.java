package com.mysite.shoppingMall.Ut;

import com.mysite.shoppingMall.Vo.IsLogined;

import javax.servlet.http.HttpSession;

public class Ut {

    private int userId;
    private int login;

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
        if (session.getAttribute("UserId") != null){
            IsLogined result = new IsLogined();
            result.setLogin(1);
            result.setUserId((int)session.getAttribute("UserId"));
            return result;
        }

        IsLogined result = new IsLogined();
        result.setLogin(0);
        result.setUserId(0);
        return result;
    }
}
