package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.QuestionForm;
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

    // 게시글 리스트 처리 =============================================
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
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // page는 조회할 페이지의 번호이고 10은 한 페이지에 보여줄 게시물의 갯수를 의미
        return pageable;
    }

    public void doDelete(Integer id){

        questionRepository.findById(id);
    }


    public Question qnaupDate(Integer id, QuestionForm questionForm) {
        Question questionTemp = getQuestion(id); //question 이라는 객체를 만듬 = questionService.getQuestion(id) 기존에 있던 내용이 담겨서 옴
        questionTemp.setSubject(questionForm.getSubject()); //기존에 있던 내용을 가지고 오고 새로 가져온 내용을 덮어 씌움.
        questionTemp.setContent(questionForm.getContent());

        questionRepository.save(questionTemp);

        return questionTemp;
    }
}