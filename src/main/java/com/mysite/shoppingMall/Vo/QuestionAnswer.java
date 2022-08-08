package com.mysite.shoppingMall.Vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QuestionAnswer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String body;

    @ManyToOne
    private Question question;
}
