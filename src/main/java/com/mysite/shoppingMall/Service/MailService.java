package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.FindPwForm;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Form.MailDto;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private UserRepository userRepository;
    private static final String FROM_ADDRESS = "no_repy@boki.com";

    @Async
    public void mailSimpleSend(MailDto mailDto) {
        mailDto.setTitle("본인인증 메일입니다.");
        mailDto.setMessage("아래 인증번호를 홈페이지에서 기입해주세요.");
        SimpleMailMessage message = new SimpleMailMessage();
        String randomAuthentication = RandomStringUtils.randomAlphanumeric(8);
        String msg = mailDto.getMessage() + "\n인증번호는 " + randomAuthentication + "입니다";
        message.setTo(mailDto.getEmail());
        message.setSubject(mailDto.getTitle());
        message.setText(msg);
        mailDto.setAuthentication(randomAuthentication);
        mailDto.setConfirmAuthentication("0");
//        mailSender.send(message);
        System.out.println(randomAuthentication);
    }

    @Async
    public void mailSimpleSend(FindPwForm findPwForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        String randomAuthentication = RandomStringUtils.randomAlphanumeric(8);
        String msg = "아래 인증번호를 홈페이지에서 기입해주세요." + "\n인증번호는 " + randomAuthentication + "입니다";
        message.setTo(findPwForm.getEmail());
        message.setSubject("비밀번호 찾기 메일입니다.");
        message.setText(msg);
        findPwForm.setIsSend("send");
        //        mailSender.send(message);
        System.out.println(randomAuthentication);
    }

    public boolean findEmail(MailDto mailDto) {
        if(!userRepository.existsByuserEmail(mailDto.getEmail())){
            return false;
        }

        return true;
    }


}