package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Form.*;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Service.MailService;
import com.mysite.shoppingMall.Service.UserService;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.IsLogined;
import com.mysite.shoppingMall.Vo.MallUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final MailService mailService;
    private final UserRepository userRepository;
    private final UserService userService;

    @RequestMapping("/login")
    public String showLogin(LoginForm loginForm, Model model) {
        model.addAttribute("Red", "text-red-500");
        return "user/login.html";
    }

    // === 로그인 ===
    @RequestMapping("/doLogin")
    public String doLogin(Model model, HttpSession session, @Valid LoginForm loginForm, BindingResult bindingResult) {

        IsLogined isLogined = Ut.isLogined(session);

        if (bindingResult.hasErrors()) {
            return "user/login.html";
        }

        if (isLogined.getLogin() == 1) {
            bindingResult.reject("", "이미 로그인이 되어있습니다.");
            return "user/login.html";
        }

        MallUser user = userService.getUser(loginForm.getEmail());

        if (user == null) {
            bindingResult.reject("", "이메일이 존재하지 않습니다.");
            return "user/login.html";
        }

        if (user.getUserEmail().equals("admin@test.com")) {
            if (user.getUserPassword().equals(loginForm.getPassword())) {
                model.addAttribute("msg", String.format("%s님 환영합니다.", user.getName()));
                model.addAttribute("replaceUri", "/");
                userService.setSession(session, user);
                return "common/js";
            }
            bindingResult.reject("", "비밀번호가 일치하지 않습니다.");
            return "user/login.html";
        }

        if (!userService.matchPw(loginForm.getPassword(), user)) {
            bindingResult.reject("", "비밀번호가 일치하지 않습니다.");
            return "user/login.html";
        }

        userService.setSession(session, user);

        model.addAttribute("msg", String.format("%s님 환영합니다.", user.getName()));
        model.addAttribute("replaceUri", "/");
        return "common/js";

    }

    // === 로그아웃 ===
    @RequestMapping("/doLogout")
    public String doLogout(HttpSession session, Model model) {
        IsLogined isLogined = Ut.isLogined(session);

        if (isLogined.getLogin() == 0) {
            model.addAttribute("msg", "이미 로그아웃 되었습니다.");
            model.addAttribute("replaceUri", "/");
            return "common/js";
        }

        userService.removeSession(session, isLogined.getUserId());


        model.addAttribute("msg", "로그아웃 되었습니다.");
        model.addAttribute("replaceUri", "/");
        return "common/js";
    }

    // === 회원가입 ===
    @RequestMapping("/join")
    public String showJoin(MailDto mailDto, JoinForm joinForm) {
        return "user/join.html";
    }

    @PostMapping("/doJoin")
    public String doJoin(MailDto mailDto, @Valid JoinForm joinForm, BindingResult bindingResult, Model model) {
        if (!mailDto.getSuccess().equals("Success")) {
            bindingResult.reject("", "이메일 인증이 필요합니다.");
            return "user/join.html";
        }

        if (bindingResult.hasErrors()) {
            return "user/join.html";
        }

        if (joinForm.getAddress1().trim().length() == 0 || joinForm.getAddress2().trim().length() == 0 || joinForm.getAddress3().trim().length() == 0 || joinForm.getAddress4().trim().length() == 0) {
            bindingResult.reject("", "주소를 입력해주세요.");
            return "user/join.html";
        }

        if (!joinForm.getPassword1().equals(joinForm.getPassword2())) {
            bindingResult.reject("", "비밀번호가 맞지 않습니다.");
            return "user/join.html";
        }

        userService.create(joinForm);


        model.addAttribute("msg", "회원가입이 완료되었습니다.");
        model.addAttribute("replaceUri", "/");
        return "common/js";
    }

    // === 회원정보 수정 ===
    @GetMapping("/myPage")
    public String myPage(JoinForm joinForm, HttpSession session, Model model) {
        MallUser mallUser = userService.getUser(session);
        String[] addressTmp = mallUser.getHomeAddress().split("\\*\\*");
        joinForm.setAddress1(addressTmp[3].trim());
        joinForm.setAddress2(addressTmp[0].trim());
        joinForm.setAddress3(addressTmp[1].trim());
        joinForm.setAddress4(addressTmp[2].trim());
        model.addAttribute("mallUser", mallUser);
        return "user/myPage.html";

    }

    @PostMapping("/myPage")
    public String myPage(@Valid JoinForm joinForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/myPage.html";
        }

        userService.modifyUser(joinForm);


        model.addAttribute("msg", "회원정보가 수정되었습니다.");
        model.addAttribute("replaceUri", "/user/myPage");
        return "common/js";
    }

    // === 회원 탈퇴 ===
//    @RequestMapping("/doDelete")
//    @ResponseBody
//    public String doDelete(String userEmail, String userPassword, HttpSession session) {
//        if (Ut.empty(userEmail)) {
//            return "이메일을 입력해주세요.";
//        }
//
//        if (Ut.empty(userPassword)) {
//            return "비밀번호를 입력해주세요.";
//        }
//
//        Optional<MallUser> opUser = userRepository.findByuserEmail(userEmail);
//        MallUser user = opUser.orElse(null);
//
//        if (user == null) {
//            return "회원이 존재하지 않습니다.";
//        }
//
////        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
////            return "비밀번호를 확인해주세요";
////        }
//
//        userRepository.delete(user);
//        session.removeAttribute("UserId");
//
//        return "탈퇴가 처리 완료되었습니다.";
//    }


    // == 이메일 찾기 ==
    @GetMapping("/findEmail")
    public String findEmail(FindEmailForm findEmailForm){
        return "user/findEmail.html";
    }

    @PostMapping("/findEmail")
    public String findEmail(@Valid FindEmailForm findEmailForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "user/findEmail.html";
        }

        MallUser mallUser = userService.getUser(findEmailForm);

        if(mallUser == null){
            bindingResult.reject("","일치하는 회원이 존재하지 않습니다.");
            return "user/findEmail.html";
        }
        model.addAttribute("mallUserEmail",mallUser.getUserEmail());

        return "user/findEmail.html";
    }
    // == 비밀번호 찾기 ==
    @RequestMapping("/findPw")
    public String findPw(FindPwForm findPwForm){
        return "user/findPw.html";
    }

}
