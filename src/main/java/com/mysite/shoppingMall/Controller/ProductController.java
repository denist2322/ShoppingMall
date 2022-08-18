package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Form.OrderSheetForm;
import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Form.ProductWriteForm;
import com.mysite.shoppingMall.Service.ProductService;
import com.mysite.shoppingMall.Service.UserService;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.IsLogined;
import com.mysite.shoppingMall.Vo.MallUser;
import com.mysite.shoppingMall.Vo.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;


//    C 생성 ==============================================
    @GetMapping("/doWrite")
    public String showWrite(ProductWriteForm productWriteForm){
        return "product/writeProduct.html";
    }
    @PostMapping("/doWrite")
    public String doWrite(@RequestParam("mainImage") List<MultipartFile> mainImage, @RequestParam("detailImage") List<MultipartFile> detailImage, ProductWriteForm productWriteForm){
        productService.doWrite(mainImage, detailImage, productWriteForm);
        return "redirect:/";
    }


    //R 읽기 ==============================================
    @RequestMapping("/list")
    public String showList(String category, Model model) {
        List<Product> productList = productService.findCategory(category);
        model.addAttribute("productList",productList);
        return "pages/productList.html";
    }

    @RequestMapping("/detail") // 단건조회
    public String showDetail(Long id, ProductBuyForm productBuyForm, Model model) {

        Product product = productService.findProduct(id);
        int discountPrice = productService.calcPrice(product, id);
        model.addAttribute("product", product);
        model.addAttribute("discountPrice", discountPrice);
        return "product/productDetail.html";
    }


    //U 수정 ==============================================
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
//    @RequestMapping("/doDelete")
//    @ResponseBody
//    public String doDelete(Long id){
//        if(!productRepository.existsById(id)){
//            return "%d번 게시물이 없습니다.".formatted(id);
//        }
//
//        Product product = productRepository.findById(id).get();
//        productRepository.delete(product);
//
//        return "%d번 게시물을 삭제했습니다.".formatted(id);
//    }

    //post 방식을 써야 ? 파라미터를 지울 수 있음.
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

        productService.setRandomNum(productBuyForm);


        return "product/orderTemp.html";
    }

    @PostMapping("/order")
    public String order(OrderSheetForm orderSheetForm, ProductBuyForm productBuyForm, HttpSession session, Model model) {
        MallUser mallUser = userService.getUser(session);

        productService.readyForOrder(orderSheetForm,mallUser);
        
        model.addAttribute("mallUser", mallUser);

        return "product/order.html";
    }

    @GetMapping("/search")
    public String search(String keyword, Model model){
        List<Product>productList = productService.searchTitleAndBody(keyword);
        model.addAttribute("productList", productList);
        model.addAttribute("keyword",keyword);
        return "pages/productList.html";
    }

}
