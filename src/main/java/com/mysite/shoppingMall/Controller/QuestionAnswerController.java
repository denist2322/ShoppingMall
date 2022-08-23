package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Repository.QuestionAnswerRepository;
import com.mysite.shoppingMall.Domain.QuestionAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class QuestionAnswerController {
    private final QuestionAnswerRepository questionAnswerRepository;

    @RequestMapping("/create")
    @ResponseBody
    public String create(String body){
        QuestionAnswer answer = new QuestionAnswer();
        answer.setRegDate(LocalDateTime.now());
        answer.setUpdateDate(LocalDateTime.now());
        answer.setBody(body);
        questionAnswerRepository.save(answer);

        return "생성완료";
    }

    @RequestMapping("/test")
    @ResponseBody
    public List<QuestionAnswer> Test(){
        return questionAnswerRepository.findAll();
    }
}
