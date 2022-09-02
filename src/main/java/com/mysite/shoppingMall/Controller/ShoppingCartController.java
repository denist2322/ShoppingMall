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
        model.addAttribute("shoppingCartList", shoppingCartList);
        return "pages/shoppingCart.html";
    }



    @RequestMapping("/addCart")
    public String addCart(ProductBuyForm productBuyForm, HttpSession session){
        shoppingCartService.addCart(productBuyForm, session);
        return "redirect:/shoppingCart";
    }

    @RequestMapping("/deleteCart")
    public String deleteCart(long id){
        shoppingCartService.deleteCart(id);
        return "redirect:/shoppingCart";
    }
}
