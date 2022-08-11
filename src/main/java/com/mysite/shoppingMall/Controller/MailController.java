package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Form.FindPwForm;
import com.mysite.shoppingMall.Form.MailDto;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Service.MailService;
import com.mysite.shoppingMall.Service.UserService;
import com.mysite.shoppingMall.Vo.MallUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MailController {
    private final MailService mailService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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
            mailDto.setSuccess("Success");
            mailDto.setFail(null);
            return "user/joinTemp.html";
        }
        mailDto.setSuccess(null);
        mailDto.setFail("Fail");
        return "user/joinTemp.html";
    }

    @PostMapping("/confirmPw")
    public String confirmPw(FindPwForm findPwForm){
        System.out.println(findPwForm.getAuthentication());
        System.out.println(findPwForm.getConfirmAuthentication());
        if (findPwForm.getAuthentication().equals(findPwForm.getConfirmAuthentication())){
            findPwForm.setIsSuccess("success");
            String passwordTmp = RandomStringUtils.randomAlphanumeric(5);
            MallUser mallUser = userService.getUser(findPwForm.getEmail());
            mallUser.setUserPassword(passwordEncoder.encode(passwordTmp));
            userRepository.save(mallUser);
            System.out.println(passwordTmp);
            return "user/pwTemp.html";
        }
        System.out.println("틀림");
        findPwForm.setIsSuccess("fail");
        return "user/pwTemp.html";
    }
}
