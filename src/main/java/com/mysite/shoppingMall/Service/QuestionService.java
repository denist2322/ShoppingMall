package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Ut.IsLogined;
import com.mysite.shoppingMall.Entity.MallUser;
import com.mysite.shoppingMall.Entity.Question;
import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Ut.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    public Page<Question> getList(int page) {
        Pageable pageable = doPageable(page);
        return this.questionRepository.findAll(pageable);
    }

    public void doWrite(String subject, String content, HttpSession session){
        IsLogined isLogined = Ut.isLogined(session);
        MallUser mallUser = userRepository.findById(isLogined.getUserId()).get();

        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setModifyDate(LocalDateTime.now());
        question.setMallUser(mallUser);
        this.questionRepository.save(question);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);

        return question.orElse(null);

    }

    @Transactional
    public Page<Question> keywordQuestion(int page, String kw){
        Pageable pageable = doPageable(page);
        return questionRepository.findBySubjectAndContent(kw,pageable);
    }

    public Pageable doPageable(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return pageable;
    }

    public void doDelete(Integer id){
        questionRepository.findById(id);
    }


}