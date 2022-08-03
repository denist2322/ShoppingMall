package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Vo.MailDto;
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
        System.out.println("send");
        mailSender.send(message);
    }
}