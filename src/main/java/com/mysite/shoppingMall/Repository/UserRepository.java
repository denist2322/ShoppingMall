package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Vo.MallUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MallUser,Integer> {
    Optional<MallUser> findByuserEmail(String userEmail);

    boolean existsByuserEmail(String userEmail);

    boolean existsByname(String nickName);

    boolean existsBycellphone(String cellphone);

    Optional<MallUser> findByNameAndBirthday(String fNname, Integer fNbirthday);
}
