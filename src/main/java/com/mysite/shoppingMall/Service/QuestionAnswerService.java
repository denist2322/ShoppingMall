package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Entity.Question;
import com.mysite.shoppingMall.Entity.QuestionAnswer;
import com.mysite.shoppingMall.Repository.QuestionAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class QuestionAnswerService {

    private final QuestionAnswerRepository questionAnswerRepository;

    public void create(Question question, String body){

        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setBody(body);
        questionAnswer.setUpdateDate(LocalDateTime.now());
        questionAnswer.setQuestion(question);
        this.questionAnswerRepository.save(questionAnswer);
    }
}

