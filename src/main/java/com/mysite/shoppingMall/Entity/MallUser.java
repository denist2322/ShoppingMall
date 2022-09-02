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

    @OneToMany(mappedBy = "mallUser", cascade = CascadeType.REMOVE)
    private List<Question> questionList;
}