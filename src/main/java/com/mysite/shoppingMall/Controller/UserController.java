package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Form.LoginForm;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Service.UserService;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.IsLogined;
import com.mysite.shoppingMall.Vo.MailDto;
import com.mysite.shoppingMall.Vo.MallUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @RequestMapping("/login")
    public String showLogin(LoginForm loginForm) {
        return "user/login.html";
    }

    // === 로그인 ===
    @RequestMapping("/doLogin")
    public String doLogin(Model model, HttpSession session, @Valid LoginForm loginForm, BindingResult bindingResult) {

        IsLogined isLogined = Ut.isLogined(session);

        if(bindingResult.hasErrors()){
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

        if(user.getUserEmail().equals("admin@test.com")){
            if(user.getUserPassword().equals(loginForm.getPassword())){
                model.addAttribute("msg", String.format("%s님 환영합니다.",user.getName()));
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

        model.addAttribute("msg", String.format("%s님 환영합니다.",user.getName()));
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
    public String showJoin(MailDto mailDto, Model model) {
        System.out.println(mailDto);
        return "user/join.html";
    }
    @RequestMapping("/doJoin")
    @ResponseBody
    public String doJoin(String userEmail, String userPassword, String name, String cellphone, Integer birthday, String homeAddress ) {
        if (Ut.empty(userEmail)) {
            return "이메일을 입력하세요.";
        }

        if (Ut.empty(userPassword)) {
            return "비밀번호를 입력하세요.";
        }

        if (Ut.empty(name)) {
            return "닉네임을 입력하세요.";
        }

        if (Ut.empty(cellphone)) {
            return "전화번호를 입력하세요.";
        }

        if (userRepository.existsByuserEmail(userEmail)) {
            return "이메일이 이미 존재합니다.";
        }

        if (userRepository.existsByname(name)) {
            return "이름이 존재합니다.";
        }

        if (userRepository.existsBycellphone(cellphone)) {
            return "이미 사용중인 전화번호입니다.";
        }

        MallUser user = new MallUser();
        user.setUserEmail(userEmail);
//        user.setUserPassword(passwordEncoder.encode(userPassword));
        user.setName(name);
        user.setCellphone(cellphone);
        user.setBirthday(birthday);
        user.setHomeAddress(homeAddress);
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }

    // === 회원정보 수정 ===
    @RequestMapping("/doModify")
    @ResponseBody
    public String doModify(Integer id, String userEmail, String userPassword, String name, String cellphone) {
        if (id == null) {
            return "id를 입력해주세요.";
        }

        if (Ut.empty(userEmail)) {
            return "수정할 이메일을 입력하세요.";
        }

        if (Ut.empty(userPassword)) {
            return "수정할 비밀번호를 입력하세요.";
        }

        if (Ut.empty(name)) {
            return "수정할 닉네임을 입력하세요.";
        }

        if (Ut.empty(cellphone)) {
            return "수정할 전화번호를 입력하세요.";
        }

        if (userRepository.existsByuserEmail(userEmail)) {
            return "이메일이 이미 존재합니다.";
        }

        if (userRepository.existsByname(name)) {
            return "이미 사용중인 닉네임입니다.";
        }

        if (userRepository.existsBycellphone(cellphone)) {
            return "이미 사용중인 전화번호입니다.";
        }

        Optional<MallUser> opMallUser = userRepository.findById(id);
        MallUser user = opMallUser.get();

        user.setUserEmail(userEmail);
//        user.setUserPassword(passwordEncoder.encode(userPassword));
        user.setCellphone(cellphone);
        user.setName(name);
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

        return "회원정보 수정이 완료되었습니다.";
    }

    // === 회원 탈퇴 ===
    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(String userEmail, String userPassword, HttpSession session) {
        if (Ut.empty(userEmail)) {
            return "이메일을 입력해주세요.";
        }

        if (Ut.empty(userPassword)) {
            return "비밀번호를 입력해주세요.";
        }

        Optional<MallUser> opUser = userRepository.findByuserEmail(userEmail);
        MallUser user = opUser.orElse(null);

        if (user == null) {
            return "회원이 존재하지 않습니다.";
        }

//        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
//            return "비밀번호를 확인해주세요";
//        }

        userRepository.delete(user);
        session.removeAttribute("UserId");

        return "탈퇴가 처리 완료되었습니다.";
    }

}
