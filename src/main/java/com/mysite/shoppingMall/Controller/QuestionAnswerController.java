package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Entity.Question;
import com.mysite.shoppingMall.Service.QuestionAnswerService;
import com.mysite.shoppingMall.Service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class QuestionAnswerController {
    private final QuestionService questionService;
    private final QuestionAnswerService questionAnswerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String body){
        Question question = this.questionService.getQuestion(id); // 아이디를 찾아와서
        this.questionAnswerService.create(question, body); // 서비스에 일을시켜 답변을 등록한다.

        return String.format("redirect:/question/detail/%s", id);
    }

}
