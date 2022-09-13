package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.FindPwForm;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Form.MailDto;
import com.mysite.shoppingMall.Entity.MallUser;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private static final String FROM_ADDRESS = "no_repy@boki.com";

    // == 회원가입 메일 발송 및 발송 확인 ==
    @Async
    public void mailSimpleSend(MailDto mailDto) {
        String title = "(쇼핑몰 SKIES)본인인증 메일입니다.";
        String randomAuthentication = sendAuthentication(mailDto.getEmail(), title);
        mailDto.setAuthentication(randomAuthentication);
        mailDto.setConfirmAuthentication("");
    //  System.out.println(randomAuthentication);
    }

    // == 비밀번호 찾기 메일 발송 및 발송 확인 ==
    @Async
    public void mailSimpleSend(FindPwForm findPwForm) {
        String title = "(쇼핑몰 SKIES)비밀번호 찾기 메일입니다.";
        String randomAuthentication = sendAuthentication(findPwForm.getEmail(), title);
        findPwForm.setAuthentication(randomAuthentication);
        findPwForm.setConfirmAuthentication("");
        findPwForm.setIsSend("send");
    //  System.out.println(randomAuthentication);
    }

    // == 메일 발송 ==
    private String sendAuthentication(String email, String title) {
        SimpleMailMessage message = new SimpleMailMessage();
        String randomAuthentication = RandomStringUtils.randomAlphanumeric(8);
        String msg = "아래 인증번호를 홈페이지에서 기입해주세요." + "\n인증번호는 " + randomAuthentication + "입니다";
        message.setTo(email);
        message.setSubject(title);
        message.setText(msg);
//        mailSender.send(message);
        System.out.println(randomAuthentication);
        return randomAuthentication;
    }

    public boolean findEmail(String email) {
        if (!userRepository.existsByuserEmail(email)) {
            return false;
        }

        return true;
    }


    public void findPw(FindPwForm findPwForm, MailDto mailDto) {
        findPwForm.setIsSuccess("success");
        String passwordTmp = RandomStringUtils.randomAlphanumeric(5);
        MallUser mallUser = userService.getUser(findPwForm.getEmail());
        mallUser.setUserPassword(passwordEncoder.encode(passwordTmp));
        userRepository.save(mallUser);
        mailDto.setTitle("(쇼핑몰 SKIES)새로운 비밀번호입니다.");
        mailDto.setMessage("아래 비밀번호로 로그인 후 마이페이지에서 변경해주세요.");
//        System.out.println(passwordTmp);
    }

    public void isSuccess(int i, MailDto mailDto) {
        if (i == 1) {
            mailDto.setSuccess("Success");
            mailDto.setFail(null);
        } else if (i == 0) {
            mailDto.setSuccess(null);
            mailDto.setFail("Fail");
        }
    }

    public void findPwForm(FindPwForm findPwForm) {
        findPwForm.setIsSuccess("fail");
    }

}