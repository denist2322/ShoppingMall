package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    //C 생성 ==============================================
    @RequestMapping("/doWrite")
    @ResponseBody
    public String doWrite(String subject, String content){
        if(Ut.empty(subject)){
            return "질문 번호를 입력해주세요.";
        }
        if(Ut.empty(content)){
            return "질문 내용을 입력해주세요.";
        }

        Question question = new Question();
        question.setCreateDate(LocalDateTime.now());
        question.setSubject(subject);
        question.setContent(content);

        questionRepository.save(question);

        return "%d번 질문 생성이 완료되었습니다.".formatted(question.getId());
    }


    //R 읽기 ==============================================
    @RequestMapping("/list")
    @ResponseBody

    public List<Question> showQuestion(){
        return questionRepository.findAll();
    }

    @RequestMapping("/detail") // 단건조회
    @ResponseBody

    public Question showDetail(Integer id){
        Question question = questionRepository.findById(id).get();

        return question;
    }


    //U 수정 ==============================================
    @RequestMapping("/doModify")
    @ResponseBody
    public String doModify(Integer id, String subject, String content){
        if(id == null){
            return "게시물 번호를 입력해주세요.";
        }
        if(Ut.empty(subject)){
            return "질문 번호를 입력해주세요.";
        }
        if(Ut.empty(content)){
            return "질문 내용을 입력해주세요.";
        }
        if(!questionRepository.existsById(id)){
            return "게시물이 없습니다.";
        }

        Question question = questionRepository.findById(id).get();

        question.setCreateDate(LocalDateTime.now());
        question.setSubject(subject);
        question.setContent(content);

        questionRepository.save(question);
        return "%d번 질문이 수정 되었습니다.".formatted(question.getId());
    }


    //D 삭제 ==============================================
    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(Integer id){
        if(!questionRepository.existsById(id)){
            return "%d번 게시물이 없습니다.".formatted(id);
        }

        Question question = questionRepository.findById(id).get();
        questionRepository.delete(question);

        return "%d번 게시물을 삭제했습니다.".formatted(id);
    }
}
