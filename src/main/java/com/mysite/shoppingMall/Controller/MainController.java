package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Entity.OrderSheet;
import com.mysite.shoppingMall.Entity.Product;
import com.mysite.shoppingMall.Service.ProductService;
import com.mysite.shoppingMall.Ut.IsLogined;
import com.mysite.shoppingMall.Ut.Ut;
import lombok.RequiredArgsConstructor;
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
        if(!notAdmin(session).equals("admin")){
            return "redirect:/";
        }

        return "pages/adminPage.html";
    }

    // == 관리자 상품 리스트 페이지 ==
    @GetMapping("/adminPage/productList")
    public String productList(HttpSession session, Model model){
        if(!notAdmin(session).equals("admin")){
            return "redirect:/";
        }

        List<Product> productList = productService.getList();
        model.addAttribute("productList",productList);
        return "pages/adminProductListPage.html";
    }

    // == 유저 주문 내역 페이지 ==
    @GetMapping("/adminPage/userOrderList")
    public String userOrderList(HttpSession session, Model model){

        if(!notAdmin(session).equals("admin")){
            return "redirect:/";
        }

        List<OrderSheet> orderSheetList = productService.getOrderList();

        model.addAttribute("orderSheetList",orderSheetList);
        return "pages/adminOrderListPage.html";
    }

    // == 유저 주문 내역 삭제 ==
    @GetMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(HttpSession session, long id){
        if(!notAdmin(session).equals("admin")){
            return "redirect:/";
        }
        productService.deleteOrder(id);
        return "success";
    }

    // == 유저 주문 내역 수정 ==
    @GetMapping("/modifyOrder")
    @ResponseBody
    public String modifyOrder(HttpSession session, long id, long nowState){
        if(!notAdmin(session).equals("admin")){
            return "redirect:/";
        }
        productService.modifyShippingOrder(id, nowState);
        return "success";
    }

    private String notAdmin(HttpSession session) {
        IsLogined islogined = Ut.isLogined(session);
        if(islogined.getAuthority() == null){
            return "notAdmin";
        }
        return "admin";
    }

    // 회사소개 페이지
    @RequestMapping("/companyintroduce")
    public String companyintroduce() {
        return "pages/companyintroduce.html";
    }

    @RequestMapping("/Terms_of_service")
    public String Terms_of_service() {
        return "pages/Termsofservice.html";
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
