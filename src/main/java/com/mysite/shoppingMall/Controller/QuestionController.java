package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Domain.Question;
import com.mysite.shoppingMall.Form.QuestionForm;
import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserRepository userRepository;

    //C 생성 ==============================================
    @GetMapping("/doWrite")
    public String doWrite(QuestionForm questionForm){

        return "QnA/write.html";
    }

    @PostMapping("/doWrite")
    public String doWrite(@Valid QuestionForm questionForm, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            return "QnA/write.html";
        }

        this.questionService.doWrite(questionForm.getSubject(), questionForm.getContent(), session);

        return "redirect:/question/list";
    }



    //R 읽기 ==============================================
    @RequestMapping("/list")
    public String showQuestion(Model model, @RequestParam(value="page", defaultValue="0") int page){
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "QnA/qna.html";

    }

    @RequestMapping("/detail/{id}") // 단건조회
    public String showDetail(Model model, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "QnA/question_detail";
    }
    
    // 검색 ==========================================================
    @RequestMapping("/detail")
    public String findDetail(@RequestParam(value="page", defaultValue="0") int page,String kw,Model model){
        Page<Question> paging = this.questionService.keywordQuestion(page, kw);
        for(Question question : paging){
            System.out.println(question.getSubject());
        }
        model.addAttribute("paging", paging);
        model.addAttribute("kw",kw);
        return "QnA/qna.html";
    }

    @GetMapping("/modify/{id}")
    public String questionModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("question", questionService.getQuestion(id));
        return "QnA/boardmodify.html";
    }

    @PostMapping("/update/{id}")
    public String questionUpdate(@PathVariable("id") Integer id, QuestionForm questionForm){ // question 제목이랑 내용을 받아옴
        Question questionTemp = questionService.getQuestion(id); //question 이라는 객체를 만듬 = questionService.getQuestion(id) 기존에 있던 내용이 담겨서 옴
        questionTemp.setSubject(questionForm.getSubject()); //기존에 있던 내용을 가지고 오고 새로 가져온 내용을 덮어 씌움.
        questionTemp.setContent(questionForm.getContent());

        questionRepository.save(questionTemp); // 기존에 있던 객체 불러오고 거기에 새로 가져온 내용을 덮어 씌워서 저장함.

        return "redirect:/question/list";
    }

    @GetMapping("/delete")
    public String doDelete(Integer id){
        Question question = questionRepository.findById(id).get();
        questionRepository.delete(question);
        return "redirect:/question/list";
    }

}
