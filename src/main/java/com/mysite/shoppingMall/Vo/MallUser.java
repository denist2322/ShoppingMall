package com.mysite.shoppingMall.Vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MallUser {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String userEmail;
    private String userPassword;
    private String name;
    private Integer birthday;
    private String homeAddress;
    private String cellphone;
}