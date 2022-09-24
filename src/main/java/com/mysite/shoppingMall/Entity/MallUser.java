package com.mysite.shoppingMall.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
// == 유저 정보 테이블 ==
public class MallUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String userEmail;
    private String userPassword;
    private String name;
    private Integer birthday;
    private String homeAddress;
    private String cellphone;

    // 유저 테이블에 질문 테이블을 연결시킨다.
    @OneToMany(mappedBy = "mallUser", cascade = CascadeType.REMOVE)
    private List<Question> questionList;
}