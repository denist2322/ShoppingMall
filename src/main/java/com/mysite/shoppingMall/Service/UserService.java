package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.FindEmailForm;
import com.mysite.shoppingMall.Form.JoinForm;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Entity.MallUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저정보를 가져온다 (이메일을 통해서)
    public MallUser getUser(String userEmail) {
        Optional<MallUser> opUser = userRepository.findByuserEmail(userEmail);
        MallUser user = opUser.orElse(null);

        return user;
    }

    // 유저정보를 가져온다 (세션을 통해서)
    public MallUser getUser(HttpSession session) {
        Integer loginId = (int) session.getAttribute("UserId");
        Optional<MallUser> opUser = userRepository.findById(loginId);
        MallUser user = opUser.orElse(null);

        return user;
    }

    // 유저정보를 가져온다 (폼 객체를 통해서)
    public MallUser getUser(FindEmailForm findEmailForm) {
        Optional<MallUser> opUser = userRepository.findByNameAndBirthday(findEmailForm.getFNname(), findEmailForm.getFNbirthday());
        MallUser user = opUser.orElse(null);

        return user;
    }

    // 비밀번호의 일치 여부를 판단함.
    public boolean matchPw(String userPassword, MallUser user) {
        return passwordEncoder.matches(userPassword, user.getUserPassword());
    }

    // 세션에 정보를 저장한다. (로그인 시)
    public void setSession(HttpSession session, MallUser user) {
        // 권한 0 => 관리자 | 권한 1 => 유저

        // 관리자일 경우(이름과 이메일이 관리자 정보일 경우)에 관리자 정보를 세션에 저장한다.
        if (user.getName().equals("관리자") && user.getUserEmail().equals("admin@test.com")) {
            session.setAttribute("UserId", user.getId());
            session.setAttribute("authority", 0);
            return;
        }

        session.setAttribute("UserId", user.getId());
        session.setAttribute("authority", 1);
    }

    // 세선을 삭제한다. (로그아웃 시)
    public void removeSession(HttpSession session, Integer userId) {
        session.removeAttribute("UserId");
        session.removeAttribute("authority");
    }

    // 유저를 추가한다. (회원가입)
    public void create(JoinForm joinForm) {
        MallUser user = new MallUser();
        user.setUserEmail(joinForm.getEmail2());
        user.setBirthday(joinForm.getBirthday2());
        user.setRegDate(LocalDateTime.now());
        createAndModify(user, joinForm);
        userRepository.save(user);
    }

    // 유저 정보를 수정한다.
    public void modifyUser(JoinForm joinForm) {
        Optional<MallUser> opMallUser = userRepository.findByuserEmail(joinForm.getEmail2());
        MallUser user = opMallUser.get();
        createAndModify(user, joinForm);
        userRepository.save(user);
    }

    // 유저 정보를 생성하고 수정하는데 중복되는 코드를 하나로 모은 것
    public void createAndModify(MallUser user, JoinForm joinForm) {
        user.setName(joinForm.getName2());
        user.setUserPassword(passwordEncoder.encode(joinForm.getPassword2()));
        String cellPhone = joinForm.getCellphone2_1() + "-" + joinForm.getCellphone2_2() + "-" + joinForm.getCellphone2_3();
        user.setCellphone(cellPhone);
        String fullAddress = joinForm.getAddress2() + "**" + joinForm.getAddress3() + "**" + joinForm.getAddress4() + "**" + joinForm.getAddress1();
        user.setHomeAddress(fullAddress);
        user.setUpdateDate(LocalDateTime.now());
    }

    // 유저를 삭제함.
    public void deleteUser(HttpSession session) {
        MallUser user = getUser(session);
        userRepository.delete(user);
    }
}
