package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.JoinForm;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Vo.MallUser;
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
        Integer loginId = (int)session.getAttribute("UserId");
        Optional<MallUser> opUser = userRepository.findById(loginId);
        MallUser user = opUser.orElse(null);

        return user;
    }

    public boolean matchPw(String userPassword, MallUser user) {
        return passwordEncoder.matches(userPassword, user.getUserPassword());
    }

    public void setSession(HttpSession session, MallUser user) {

            if(user.getName().equals("관리자")){
                session.setAttribute("UserId", user.getId());
                session.setAttribute("authority", "0");
            }

            session.setAttribute("UserId", user.getId());
            session.setAttribute("authority", "1");

    }

    public void removeSession(HttpSession session, Integer userId){
        session.removeAttribute("UserId");
    }

    public void create(JoinForm joinForm) {
        MallUser user = new MallUser();
        user.setUserEmail(joinForm.getEmail2());
        user.setUserPassword(passwordEncoder.encode(joinForm.getPassword2()));
        user.setName(joinForm.getName());
        user.setCellphone(joinForm.getCellphone());
        user.setBirthday(joinForm.getBirthday());
        String fullAddress = joinForm.getAddress2() + " " + joinForm.getAddress3() + joinForm.getAddress4() + joinForm.getAddress1() ;
        user.setHomeAddress(fullAddress);
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);
    }

}
