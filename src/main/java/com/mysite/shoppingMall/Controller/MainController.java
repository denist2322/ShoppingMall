package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Domain.IsLogined;
import com.mysite.shoppingMall.Domain.OrderSheet;
import com.mysite.shoppingMall.Domain.Product;
import com.mysite.shoppingMall.Repository.ProductRepository;
import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Repository.ShoppingCartRepository;
import com.mysite.shoppingMall.Service.ProductService;
import com.mysite.shoppingMall.Service.ShoppingCartService;
import com.mysite.shoppingMall.Ut.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private ShoppingCartService shoppingCartService;
    private final ProductService productService;

    @RequestMapping("/main")
    public String showMain() {
        return "pages/main.html";
    }

    @RequestMapping("/")
    public String rootMain() {
        return "redirect:/main";
    }

    // == 관리자 페이지 ==
    @GetMapping("/adminPage")
    public String adminPage(HttpSession session){
        IsLogined islogined = Ut.isLogined(session);
        if(islogined.getAuthority() == null){
            return "redirect:/";
        }

        return "pages/adminPage.html";
    }

    // == 관리자 상품 리스트 페이지 ==
    @GetMapping("/adminPage/productList")
    public String productList(HttpSession session, Model model){
        IsLogined islogined = Ut.isLogined(session);
        if(islogined.getAuthority() == null){
            return "redirect:/";
        }

        List<Product> productList = productService.getList();
        model.addAttribute("productList",productList);
        return "pages/adminProductListPage.html";
    }

    // == 유저 주문 내역 페이지 ==
    @GetMapping("/adminPage/userOrderList")
    public String userOrderList(Model model){

        List<OrderSheet> orderSheetList = productService.getOrderList();

        model.addAttribute("orderSheetList",orderSheetList);
        return "pages/adminOrderListPage.html";
    }

    // == 유저 주문 내역 삭제 ==
    @GetMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(long id){
        productService.deleteOrder(id);
        return "success";
    }

    // == 유저 주문 내역 수정 ==
    @GetMapping("/modifyOrder")
    @ResponseBody
    public String modifyOrder(long id, long nowState){
        productService.modifyShippingOrder(id, nowState);
        return "success";
    }

    // 잠깐 확인좀
//    @RequestMapping("/test")
//    public String showTest(Model model, HttpSession session) {
//        IsLogined isLogined = Ut.isLogined(session);
//        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findByMallUserId(isLogined.getUserId());
//        model.addAttribute("shoppingCartList", shoppingCartList);
//        return "user/orderHistory.html";
//  }

}
