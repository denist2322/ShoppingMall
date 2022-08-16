package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Form.FindPwForm;
import com.mysite.shoppingMall.Form.MailDto;
import com.mysite.shoppingMall.Service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MailController {
    private final MailService mailService;

//    @GetMapping("/mail")
//    public String getMail(MailDto mailDto) {
//        return "user/join";
//    }

    // == 메일보내기 ==
    @PostMapping("/mail")
    public String execMail(MailDto mailDto, Model model) {
        if(mailService.findEmail(mailDto.getEmail())){
            model.addAttribute("msg", "이메일이 이미 존재합니다.");
            model.addAttribute("replaceUri", "/user/join");
            return "common/js";
        }
        mailService.mailSimpleSend(mailDto);
        return "user/joinTemp.html";
    }

    @PostMapping("/findPwMail")
    public String findPwMail(FindPwForm findPwForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "user/pwTemp.html";
        }

        if(!mailService.findEmail(findPwForm.getEmail())){
            model.addAttribute("msg", "회원이 존재하지 않습니다.");
            model.addAttribute("replaceUri", "/user/findPw");
            return "common/js";
        }

        mailService.mailSimpleSend(findPwForm);
        return "user/pwTemp.html";
    }

    // == 인증 확인 ==
    @PostMapping("/confirm")
    public String confirm(MailDto mailDto) {
        if (mailDto.getAuthentication().equals(mailDto.getConfirmAuthentication())) {
            mailService.isSuccess(1, mailDto);
            return "user/joinTemp.html";
        }
        mailService.isSuccess(0, mailDto);

        return "user/joinTemp.html";
    }

    @PostMapping("/confirmPw")
    public String confirmPw(FindPwForm findPwForm){
        if (findPwForm.getAuthentication().equals(findPwForm.getConfirmAuthentication())){
            mailService.findPw(findPwForm);
            return "user/pwTemp.html";
        }
        mailService.findPwForm(findPwForm);
        return "user/pwTemp.html";
    }
}
