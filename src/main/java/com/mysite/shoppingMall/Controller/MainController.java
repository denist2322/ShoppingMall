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

    // == main 홈 페이지 ==
    @GetMapping("/main")
    public String showMain() {
        return "pages/main.html";
    }

    @RequestMapping("/")
    public String rootMain() {
        return "redirect:/main";
    }

    // == 관리자 페이지 ==
    @GetMapping("/adminPage")
    public String adminPage(HttpSession session) {
        if (!Ut.notAdmin(session).equals("admin")) {
            return "redirect:/";
        }

        return "pages/adminPage.html";
    }

    // == 관리자 상품 리스트 페이지 ==
    @GetMapping("/adminPage/productList")
    public String productList(HttpSession session, Model model) {
        if (!Ut.notAdmin(session).equals("admin")) {
            return "redirect:/";
        }

        List<Product> productList = productService.getList();
        model.addAttribute("productList", productList);
        return "pages/adminProductListPage.html";
    }

    // == 유저 주문 내역 페이지 ==
    @GetMapping("/adminPage/userOrderList")
    public String userOrderList(HttpSession session, Model model) {

        if (!Ut.notAdmin(session).equals("admin")) {
            return "redirect:/";
        }

        List<OrderSheet> orderSheetList = productService.getOrderList();

        model.addAttribute("orderSheetList", orderSheetList);
        return "pages/adminOrderListPage.html";
    }

    // == 유저 주문 내역 삭제 ==
    @GetMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(HttpSession session, long id) {
        if (!Ut.notAdmin(session).equals("admin")) {
            return "redirect:/";
        }
        productService.deleteOrder(id);
        return "success";
    }

    // == 유저 주문 내역 수정 ==
    @GetMapping("/modifyOrder")
    @ResponseBody
    public String modifyOrder(HttpSession session, long id, long nowState) {
        if (!Ut.notAdmin(session).equals("admin")) {
            return "redirect:/";
        }
        productService.modifyShippingOrder(id, nowState);
        return "success";
    }

    // 회사소개 페이지
    @GetMapping("/companyintroduce")
    public String companyintroduce() {
        return "pages/companyintroduce.html";
    }

    // == 이용약관 페이지 ==
    @GetMapping("/Terms_of_service")
    public String Terms_of_service() {
        return "pages/Termsofservice.html";
    }

}
