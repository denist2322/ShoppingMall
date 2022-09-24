package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Entity.ShoppingCart;
import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Service.ShoppingCartService;
import com.mysite.shoppingMall.Ut.IsLogined;
import com.mysite.shoppingMall.Ut.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;


    // == 장바구니 ==
    @RequestMapping("/shoppingCart")
    public String shoppingCart(HttpSession session, Model model) {
        IsLogined isLogined = Ut.isLogined(session);
        List<ShoppingCart> shoppingCartList = shoppingCartService.getCartList(isLogined.getUserId());
        List<Integer> ShoppingDetailPrice = shoppingCartService.getPriceList(isLogined.getUserId());

        model.addAttribute("shoppingCartList", shoppingCartList);
        model.addAttribute("ShoppingDetailPrice", ShoppingDetailPrice);

        return "pages/shoppingCart.html";
    }

    // == 장바구니 상품 추가 ==
    @RequestMapping("/addCart")
    public String addCart(ProductBuyForm productBuyForm, HttpSession session) {
        shoppingCartService.addCart(productBuyForm, session);
        return "redirect:/shoppingCart";
    }

    // == 장바구니 상품 삭제 ==
    @RequestMapping("/deleteCart")
    public String deleteCart(long id) {
        shoppingCartService.deleteCart(id);
        return "redirect:/shoppingCart";
    }

    // == 장바구니에서 체크여부 변환 ==
    @RequestMapping("/isChecked")
    @ResponseBody
    public String isChecked(int check, long id) {
        shoppingCartService.changeChecked(check, id);
        return "success";
    }

    // == 장바구니 전체 선택 혹은 해제 ==
    @RequestMapping("/allChecked")
    @ResponseBody
    public List<Integer> isChecked(int check, HttpSession session) {
        IsLogined isLogined = Ut.isLogined(session);
        shoppingCartService.changeChecked(check, session);
        List<Integer> ShoppingDetailPrice = shoppingCartService.getPriceList(isLogined.getUserId());
        return ShoppingDetailPrice;
    }

}
