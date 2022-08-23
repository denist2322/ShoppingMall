package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.FindEmailForm;
import com.mysite.shoppingMall.Form.JoinForm;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Domain.MallUser;
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

    public MallUser getUser(String userEmail) {
        Optional<MallUser> opUser = userRepository.findByuserEmail(userEmail);
        MallUser user = opUser.orElse(null);

        return user;
    }

    public MallUser getUser(HttpSession session) {
        Integer loginId = (int) session.getAttribute("UserId");
        Optional<MallUser> opUser = userRepository.findById(loginId);
        MallUser user = opUser.orElse(null);

        return user;
    }

    public MallUser getUser(FindEmailForm findEmailForm) {
        Optional<MallUser> opUser = userRepository.findByNameAndBirthday(findEmailForm.getFNname(), findEmailForm.getFNbirthday());
        MallUser user = opUser.orElse(null);

        return user;
    }

    public boolean matchPw(String userPassword, MallUser user) {
        return passwordEncoder.matches(userPassword, user.getUserPassword());
    }

    public void setSession(HttpSession session, MallUser user) {

        if (user.getName().equals("관리자") && user.getUserEmail().equals("admin@test.com")) {
            session.setAttribute("UserId", user.getId());
            session.setAttribute("authority", 0);
            return;
        }

        session.setAttribute("UserId", user.getId());
        session.setAttribute("authority", 1);
    }

    public void removeSession(HttpSession session, Integer userId) {
        session.removeAttribute("UserId");
        session.removeAttribute("authority");
    }

    public void create(JoinForm joinForm) {
        MallUser user = new MallUser();
        user.setUserEmail(joinForm.getEmail2());
        user.setBirthday(joinForm.getBirthday2());
        user.setRegDate(LocalDateTime.now());
        createAndModify(user, joinForm);
        userRepository.save(user);
    }

    public void modifyUser(JoinForm joinForm) {
        Optional<MallUser> opMallUser = userRepository.findByuserEmail(joinForm.getEmail2());
        MallUser user = opMallUser.get();
        createAndModify(user, joinForm);
        userRepository.save(user);
    }

    public void createAndModify(MallUser user, JoinForm joinForm){
        user.setName(joinForm.getName2());
        user.setUserPassword(passwordEncoder.encode(joinForm.getPassword2()));
        String cellPhone = joinForm.getCellphone2_1() + "-" + joinForm.getCellphone2_2() + "-" + joinForm.getCellphone2_3();
        user.setCellphone(cellPhone);
        String fullAddress = joinForm.getAddress2() + "**" + joinForm.getAddress3() + "**" + joinForm.getAddress4() + "**" + joinForm.getAddress1();
        user.setHomeAddress(fullAddress);
        user.setUpdateDate(LocalDateTime.now());
    }

}
