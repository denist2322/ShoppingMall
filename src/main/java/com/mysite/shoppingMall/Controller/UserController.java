package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Entity.MallUser;
import com.mysite.shoppingMall.Entity.OrderSheet;
import com.mysite.shoppingMall.Form.*;
import com.mysite.shoppingMall.Service.ProductService;
import com.mysite.shoppingMall.Service.UserService;
import com.mysite.shoppingMall.Ut.IsLogined;
import com.mysite.shoppingMall.Ut.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    // == 로그인 ==
    // 로그인 페이지 출력
    @RequestMapping("/login")
    public String showLogin(LoginForm loginForm, Model model) {
        model.addAttribute("Red", "text-red-500");
        return "user/login.html";
    }

    // 실제로 로그인이 이루어짐
    @RequestMapping("/doLogin")
    public String doLogin(Model model, HttpSession session, @Valid LoginForm loginForm, BindingResult bindingResult) {

        IsLogined isLogined = Ut.isLogined(session);

        // Valid를 검사한다.
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

    // == 회원가입 ==
    @RequestMapping("/join")
    public String showJoin(MailDto mailDto, JoinForm joinForm) {
        return "user/join.html";
    }

    // == 실제 회원가입이 이루어짐 ==
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
            bindingResult.rejectValue("addressError", "", "주소를 입력해주세요.");
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

    // == 마이페이지 화면 출력 ==
    @GetMapping("/myPage")
    public String myPage(JoinForm joinForm, HttpSession session, Model model) {
        MallUser mallUser = userService.getUser(session);
        List<Integer> shippingState = productService.getShippingState(session);

        productService.convertAdAndPhone(joinForm, mallUser);
        model.addAttribute("mallUser", mallUser);
        model.addAttribute("shippingState", shippingState);

        return "user/myPage.html";

    }

    // == 마이페이지 ==
    @PostMapping("/myPage")
    public String myPage(@Valid JoinForm joinForm, BindingResult bindingResult, Model model, HttpSession session) {
        IsLogined isLogined = Ut.isLogined(session);
        if (bindingResult.hasErrors()) {
            return "user/myPage.html";
        }

        if (isLogined.getLogin() == 0 || isLogined.getAuthority() == 0) {
            model.addAttribute("msg", "잘못된 접근입니다.");
            model.addAttribute("historyBack", "/user/myPage");

            return "common/js";
        }


        userService.modifyUser(joinForm);

        model.addAttribute("msg", "회원정보가 수정되었습니다.");
        model.addAttribute("replaceUri", "/user/myPage");

        return "common/js";
    }

    // == 회원 탈퇴 ==
    @GetMapping("/doDelete")
    @ResponseBody
    public String doDelete(HttpSession session) {
        IsLogined isLogined = Ut.isLogined(session);

        if (isLogined.getLogin() == 0 || isLogined.getAuthority() == 0) {
            return """
                    <script>
                    alert("잘못된 접근입니다.");
                    history.back();
                    </script>
                    """;
        }
        userService.deleteUser(session);
        return """
                <script>
                alert("탈퇴처리가 완료되었습니다.");
                location.replace("/");
                </script>
                """;
    }


    // == 이메일 찾기 ==
    @GetMapping("/findEmail")
    public String findEmail(FindEmailForm findEmailForm) {
        return "user/findEmail.html";
    }

    @PostMapping("/findEmail")
    public String findEmail(@Valid FindEmailForm findEmailForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/findEmail.html";
        }

        MallUser mallUser = userService.getUser(findEmailForm);

        if (mallUser == null) {
            model.addAttribute("noUser","noUser");
            return "user/findEmail.html";
        }
        model.addAttribute("mallUserEmail", mallUser.getUserEmail());

        return "user/findEmail.html";
    }

    // == 비밀번호 찾기 ==
    @RequestMapping("/findPw")
    public String findPw(FindPwForm findPwForm) {
        return "user/findPw.html";
    }

    // == 주문 처리 현황 페이지 ==
    @RequestMapping("/orderHistory")
    public String showTest(Model model, HttpSession session) {
        MallUser mallUser = userService.getUser(session);
        IsLogined isLogined = Ut.isLogined(session);
        List<OrderSheet> orderSheetList = productService.getOrderList(isLogined.getUserId());
        List<Integer> shippingState = productService.getShippingState(session);
        model.addAttribute("mallUser", mallUser);
        model.addAttribute("orderSheetList", orderSheetList);
        model.addAttribute("shippingState", shippingState);
        return "user/orderHistory.html";
    }

}
