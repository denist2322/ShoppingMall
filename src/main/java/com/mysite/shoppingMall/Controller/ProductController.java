package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Service.ProductService;
import com.mysite.shoppingMall.Vo.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    
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
    public String showDetail(Long id, Model model){
        Product product = productService.findProduct(id);
        model.addAttribute("product",product);
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

}
