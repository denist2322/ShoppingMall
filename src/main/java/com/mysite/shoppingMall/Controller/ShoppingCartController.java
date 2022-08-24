package com.mysite.shoppingMall.Controller;

<<<<<<< Updated upstream
import com.mysite.shoppingMall.Domain.IsLogined;
import com.mysite.shoppingMall.Domain.MallUser;
import com.mysite.shoppingMall.Domain.ShoppingCart;
=======
import com.mysite.shoppingMall.Domain.MallUser;
>>>>>>> Stashed changes
import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Service.ShoppingCartService;
import com.mysite.shoppingMall.Service.UserService;
import com.mysite.shoppingMall.Ut.Ut;
<<<<<<< Updated upstream
=======
import com.mysite.shoppingMall.Domain.IsLogined;
import com.mysite.shoppingMall.Domain.ShoppingCart;

>>>>>>> Stashed changes
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

    private final UserService userService;

    // == 장바구니 ==
    @RequestMapping("/shoppingCart")
    public String shoppingCart(HttpSession session, Model model) {
        IsLogined isLogined = Ut.isLogined(session);
        List<ShoppingCart> shoppingCartList = shoppingCartService.getCartList(isLogined.getUserId());
        model.addAttribute("shoppingCartList", shoppingCartList);
        return "pages/shoppingCart.html";
    }

    @RequestMapping("/shippingLookup")
    public String showTest(Model model, HttpSession session) {
        MallUser mallUser = userService.getUser(session);

        IsLogined isLogined = Ut.isLogined(session);
        List<ShoppingCart> shoppingCartList = shoppingCartService.getCartList(isLogined.getUserId());

        model.addAttribute("mallUser", mallUser);
        model.addAttribute("shoppingCartList", shoppingCartList);
        return "user/ordershpping.html";
    }

    @RequestMapping("/addCart")
    public String addCart(ProductBuyForm productBuyForm, HttpSession session){
        shoppingCartService.addCart(productBuyForm, session);
        return "redirect:/shoppingCart";
    }
}
