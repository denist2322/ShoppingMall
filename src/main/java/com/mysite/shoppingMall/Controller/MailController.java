package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Service.MailService;
import com.mysite.shoppingMall.Vo.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MailController {
    private final MailService mailService;

//    @GetMapping("/mail")
//    public String getMail(MailDto mailDto) {
//        return "user/join";
//    }

    @PostMapping("/mail")
    public String execMail(MailDto mailDto) {
        mailService.mailSimpleSend(mailDto);
        return "user/joinTemp.html";
    }

    @PostMapping("/confirm")
    public String confirm(MailDto mailDto) {
        if (mailDto.getAuthentication().equals(mailDto.getConfirmAuthentication())) {
            mailDto.setSuccess("Success");
            mailDto.setFail(null);
            return "user/joinTemp.html";
        }
        mailDto.setSuccess(null);
        mailDto.setFail("Fail");
        return "user/joinTemp.html";
    }
}
