package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Form.ProductBuyForm;
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

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;


    //C 생성 ==============================================
//    @RequestMapping("/doWrite")
//    @ResponseBody
//    public String doWrite(String title, String body){
//        if(Ut.empty(title)){
//            return "제목을 입력해주세요.";
//        }
//        if(Ut.empty(body)){
//            return "내용을 입력해주세요.";
//        }
//
//        Product product = new Product();
//        product.setRegDate(LocalDateTime.now());
//        product.setUpdateDate(LocalDateTime.now());
//        product.setTitle(title);
//        product.setBody(body);
//
//        productRepository.save(product);
//
//        return "%s 상품이 생성되었습니다.".formatted(product.getTitle());
//    }


    //R 읽기 ==============================================
//    @RequestMapping("/list")
//    @ResponseBody
//    public List<Product> showList(){
//        return productRepository.findAll();
//    }

    @RequestMapping("/detail") // 단건조회
    public String showDetail(Long id, ProductBuyForm productBuyForm, Model model) {
        Product product = productService.findProduct(id);
        int discountPrice = (int) (product.getPrice() * (1 - (double) product.getDiscount() / 100));
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

        if(isLogined.getLogin() == 0){
            return "redirect:/user/login";
        }

        if (productBuyForm.getOrderColor().equals("no") || productBuyForm.getOrderSize().equals("no")) {
            bindingResult.reject("", "색상 혹은 사이즈를 선택해주세요.");
            return "redirect:/product/detail?id=" + productBuyForm.getProductId();
        }

        int orderNumber = (int)(Math.random()*10000000);
        productBuyForm.setOrderNumber(orderNumber);

        return "product/orderTemp.html";
    }

    @PostMapping("/order")
    public String order(ProductBuyForm productBuyForm, HttpSession session, Model model){
        MallUser mallUser = userService.getUser(session);
        String[] emailTemp = mallUser.getUserEmail().split("\\@");
        productBuyForm.setOrderEmail1(emailTemp[0]);
        productBuyForm.setOrderEmail2(emailTemp[1]);

        model.addAttribute("mallUser", mallUser);

        return "product/order.html";
    }

}

/*
session.loginUserId
List<Question> questionList = questionRepository.findBy



*/