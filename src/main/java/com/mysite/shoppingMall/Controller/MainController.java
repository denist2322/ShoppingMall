package com.mysite.shoppingMall.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/test")
    public String showTest(){
        return """
                <script>
                alert("하이요");
                location.replace(/product/list);
                </script>
                """;
    }
    @RequestMapping("/")
        public String rootMain() {
            return "redirect:product/list";
        }

}
