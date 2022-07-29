package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Repository.ProductRepository;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    
    //C 생성 ==============================================
    @RequestMapping("/doWrite")
    @ResponseBody
    public String doWrite(String title, String body){
        if(Ut.empty(title)){
            return "제목을 입력해주세요.";
        }
        if(Ut.empty(body)){
            return "내용을 입력해주세요.";
        }

        Product product = new Product();
        product.setRegDate(LocalDateTime.now());
        product.setUpdateDate(LocalDateTime.now());
        product.setTitle(title);
        product.setBody(body);

        productRepository.save(product);

        return "%d번 게시물이 생성되었습니다.".formatted(product.getId());
    }


    //R 읽기 ==============================================
    @RequestMapping("/list")
    @ResponseBody
    public List<Product> showList(){
        return productRepository.findAll();
    }

    @RequestMapping("/detail") // 단건조회
    @ResponseBody
    public Product showDetail(Long id){
        return productRepository.findById(id).get();
    }


    //U 수정 ==============================================
    @RequestMapping("/doModify")
    @ResponseBody
    public String doModify(Long id, String title, String body){
        if(id == null){
            return "게시물 번호를 입력해주세요.";
        }
        if(Ut.empty(title)){
            return "제목을 입력해주세요.";
        }
        if(Ut.empty(body)){
            return "내용을 입력해주세요.";
        }
        if(!productRepository.existsById(id)){
            return "게시물이 없습니다.";
        }

        Product product = productRepository.findById(id).get();

        product.setTitle(title);
        product.setBody(body);
        productRepository.save(product);

        return "%d번 게시물이 수정되었습니다.".formatted(product.getId());
    }


    //D 삭제 ==============================================
    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(Long id){
        if(!productRepository.existsById(id)){
            return "%d번 게시물이 없습니다.".formatted(id);
        }

        Product product = productRepository.findById(id).get();
        productRepository.delete(product);

        return "%d번 게시물을 삭제했습니다.".formatted(id);
    }

}
