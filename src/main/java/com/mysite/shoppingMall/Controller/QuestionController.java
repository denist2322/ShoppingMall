package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Ut.IsLogined;
import com.mysite.shoppingMall.Entity.Question;
import com.mysite.shoppingMall.Form.QuestionForm;
import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Service.QuestionService;
import com.mysite.shoppingMall.Ut.Ut;
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


    //C qna 게시글 생성 ==============================================
    @GetMapping("/doWrite")
    public String doWrite(QuestionForm questionForm){

        return "QnA/write.html";
    }

    @PostMapping("/doWrite")
    public String doWrite(@Valid QuestionForm questionForm, BindingResult bindingResult, HttpSession session, Model model){
        if(bindingResult.hasErrors()){
            return "QnA/write.html";
        }

        this.questionService.doWrite(questionForm.getSubject(), questionForm.getContent(), session);

        model.addAttribute("msg", "글 작성이 완료되었습니다.");
        model.addAttribute("replaceUri", "/question/list");
        return "common/js.html";
    }


    // qna 게시글 조회 ==============================================
    @RequestMapping("/list")
    public String showQuestion(Model model, @RequestParam(value="page", defaultValue="0") int pageNo){
        Page<Question> paging = this.questionService.getList(pageNo);
        model.addAttribute("paging", paging);
        model.addAttribute("pageNo",pageNo);
        return "QnA/qna.html";
    }

    // qna 게시글 단건 조회 ==============================================
    @RequestMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Integer id, HttpSession session){
        IsLogined isLogined = Ut.isLogined(session);

        Question question = questionService.getQuestion(id);
        model.addAttribute("question",question);

        if(isLogined.getUserId() == question.getMallUser().getId() || (isLogined.getAuthority() != null && isLogined.getAuthority() == 0 )) {
            return "QnA/question_detail";
        }

        model.addAttribute("msg", "권한이 없습니다.");
        model.addAttribute("historyBack", "true");

        return "common/js.html";
    }
    
    // 검색 ==========================================================
    @RequestMapping("/detail")
    public String findDetail(@RequestParam(value="page", defaultValue="0") int page,String kw,Model model){
        Page<Question> paging = this.questionService.keywordQuestion(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw",kw);
        return "QnA/qna.html";
    }

    // 게시글 수정 ==========================================================
    @RequestMapping("/modify")
    public String questionModify(Integer questionId, Integer mallUserId, Model model, HttpSession session){
        IsLogined isLogined = Ut.isLogined(session); // 세션 사용해서 로그인 확인

        if((isLogined.getAuthority() != null && isLogined.getAuthority() == 0 )){ // 관리자가 null 이 아니다. 그리고 관리자다.
            Question question = questionService.getQuestion(questionId); //questionId 를 가져옴
            model.addAttribute("question",question);
            return "QnA/boardmodify.html";
        }
        if(isLogined.getUserId() != mallUserId ){ // 글쓴 Id 랑 로그인 Id가 다르면
            model.addAttribute("msg", "수정 권한이 없습니다.");
            model.addAttribute("historyBack","true");

            return "common/js.html";
        }


        Question question = questionService.getQuestion(questionId);
//        Question question = questionRepository.findById(questionId).get();
        model.addAttribute("question",question);

        return "QnA/boardmodify.html";
    }

    // 수정페이지 ==========================================================
    @PostMapping("/update/{id}")
    public String questionUpdate(@PathVariable("id") Integer id, QuestionForm questionForm){ // questionForm 에서 제목이랑 내용을 받아옴
        Question questionTemp = questionService.doUpdate(id, questionForm);
        return "redirect:/question/list";
    }

    @RequestMapping("/delete")
    public String doDelete(Integer questionId, Integer mallUserId, HttpSession session, Model model){
        IsLogined isLogined = Ut.isLogined(session);

        if(isLogined.getLogin() == 0 ){ // 로그인이 비어있는지 확인.

            model.addAttribute("msg", "로그인후 이용해주세요.");
            model.addAttribute("historyBack","true");

            return "common/js.html";
        }

        if(isLogined.getUserId() != mallUserId && isLogined.getAuthority() != 0){ // 글쓴 Id 와 로그인유저 Id 다르거나 and (관리자가 0임) 관리자가 아니라면

            model.addAttribute("msg", "삭제 권한이 없습니다.");
            model.addAttribute("historyBack","true");

            return "common/js.html";
        }

        questionService.doDelete(questionId);

        return "redirect:/question/list";
    }

}
