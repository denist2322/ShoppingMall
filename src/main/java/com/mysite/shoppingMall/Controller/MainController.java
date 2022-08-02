package com.mysite.shoppingMall.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/main")
    public String showMain(){
        return "pages/main.html";
    }
    @RequestMapping("/")
        public String rootMain() {
            return "redirect:/main";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String showTest(){
        return """
                <script>
                alert("안녕");
                location.replace("/");
                </script>
                """;
    }

}
