package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Repository.ProductRepository;
import com.mysite.shoppingMall.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @RequestMapping("/main")
    public String showMain() {
        return "pages/main.html";
    }

    @RequestMapping("/")
    public String rootMain() {
        return "redirect:/main";
    }

    @RequestMapping("/test")
    public String showTest() {
        return "/";
    }

}
