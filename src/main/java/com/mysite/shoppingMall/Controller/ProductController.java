package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Form.OrderSheetForm;
import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Form.ProductWriteForm;
import com.mysite.shoppingMall.Service.ProductService;
import com.mysite.shoppingMall.Service.ShoppingCartService;
import com.mysite.shoppingMall.Service.UserService;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Domain.IsLogined;
import com.mysite.shoppingMall.Domain.MallUser;
import com.mysite.shoppingMall.Domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    private final ShoppingCartService shoppingCartService;


    //C 생성 ==============================================
    @GetMapping("/doWrite")
    public String showWrite(ProductWriteForm productWriteForm, HttpSession session, Model model){
        IsLogined isLogined = Ut.isLogined(session);
        if(isLogined.getAuthority() == null){
            return "redirect:/";
        }
        Product product = new Product();
        model.addAttribute("product",product);

        return "product/writeProduct.html";
    }
    @PostMapping("/doWrite")
    public String doWrite(@RequestParam("mainImage") List<MultipartFile> mainImage, @RequestParam("detailImage") List<MultipartFile> detailImage, ProductWriteForm productWriteForm){
        productService.doWrite(mainImage, detailImage, productWriteForm);
        return "redirect:/adminPage";
    }


    //R 읽기 ==============================================
    @RequestMapping("/list")
    public String showList(String category, Model model) {
        List<Product> productList = productService.findCategory(category);
        model.addAttribute("productList",productList);
        return "pages/productList.html";
    }

    @RequestMapping("/detail") // 단건조회
    public String showDetail(Long id, ProductBuyForm productBuyForm, HttpSession session ,Model model) {

        Product product = productService.findProduct(id);
        int discountPrice = productService.calcPrice(product, id);
        model.addAttribute("product", product);
        model.addAttribute("discountPrice", discountPrice);
        return "product/productDetail.html";
    }


    //U 수정 ==============================================
    @GetMapping("/doModify")
    public String showModify(Long id, ProductWriteForm productWriteForm, HttpSession session, Model model){
        IsLogined isLogined = Ut.isLogined(session);
        if(isLogined.getAuthority() == null){
            return "redirect:/";
        }
        Product product = productService.findProduct(id);
        String color = productService.setColorString(product.getProductColorList());
        String size = productService.setSizeString(product.getProductSizeList());
        System.out.println(product.getTitle());

        model.addAttribute("product",product);
        model.addAttribute("color",color);
        model.addAttribute("size",size);
        return "product/writeProduct.html";
    }

    @PostMapping("/doModify")
    public String doModify(@RequestParam("mainImage") List<MultipartFile> mainImage, @RequestParam("detailImage") List<MultipartFile> detailImage, ProductWriteForm productWriteForm){
        productService.doWrite(mainImage, detailImage, productWriteForm);
        return "redirect:/";
    }
//    @RequestMapping("/doModify")
//    @ResponseBody
//    public String doModify(Long id, String title, String body){
//        if(id == null){
//            return "게시물 번호를 입력해주세요.";
//        }
//        if(Ut.empty(title)){
//            return "제목을 입력해주세요.";
//        }
//        if(Ut.empty(body)){
//            return "내용을 입력해주세요.";
//        }
//        if(!productRepository.existsById(id)){
//            return "게시물이 없습니다.";
//        }
//
//        Product product = productRepository.findById(id).get();
//
//        product.setTitle(title);
//        product.setBody(body);
//        productRepository.save(product);
//
//        return "%d번 게시물이 수정되었습니다.".formatted(product.getId());
//    }


    //D 삭제 ==============================================
    @RequestMapping("/doDelete")
    public String doDelete(Long id, Model model){

        if(!productService.isExists(id)){
            model.addAttribute("msg", "게시물이 존재하지 않습니다.");
            model.addAttribute("historyBack", "true");
            return "common/js";
        }

        productService.doDelete(id);

        model.addAttribute("msg", "삭제 완료되었습니다.");
        model.addAttribute("historyBack", "true");
        return "common/js";
    }

    //주문서 전달 (새로고침시 주문번호 바뀌는거 방지) ===============================
    @GetMapping("/orderSheetTmp")
    public String orderSheet(ProductBuyForm productBuyForm, BindingResult bindingResult, HttpSession session, Model model) {
        IsLogined isLogined = Ut.isLogined(session);

        if (isLogined.getLogin() == 0) {
            return "redirect:/user/login";
        }

        if (productBuyForm.getOrderColor().equals("no") || productBuyForm.getOrderSize().equals("no")) {
            bindingResult.reject("", "색상 혹은 사이즈를 선택해주세요.");
            return "redirect:/product/detail?id=" + productBuyForm.getProductId();
        }

        productService.setReady(productBuyForm);

        return "product/orderTemp.html";
    }

    // 주문창 =======================================
    @PostMapping("/order")
    public String order(OrderSheetForm orderSheetForm, ProductBuyForm productBuyForm, HttpSession session, Model model) {
        MallUser mallUser = userService.getUser(session);

        productService.readyForOrder(orderSheetForm,mallUser);
        
        model.addAttribute("mallUser", mallUser);

        return "product/order.html";
    }
    // 주문 내역 저장 ================================
    @GetMapping("/saveOrder")
    public String saveOrder(){
        return "pages/orderSuccess.html";
    }

    // 검색 =========================================
    @GetMapping("/search")
    public String search(String keyword, Model model){
        List<Product>productList = productService.searchTitleAndBody(keyword);
        model.addAttribute("productList", productList);
        model.addAttribute("keyword",keyword);
        return "pages/productList.html";
    }

}
