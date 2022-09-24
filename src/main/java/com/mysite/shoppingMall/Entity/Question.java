package com.mysite.shoppingMall.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
// == 질문 테이블 ==
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createDate;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime modifyDate;
    // 유저 테이블에 질문 테이블을 연결 시킨다.
    @ManyToOne
    private MallUser mallUser;
    // 질문 테이블에 질문 답변 테이블을 연결 시킨다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    // 질문을 삭제하면 그에 달린 답변들도 모두 함께 삭제하기 위해서 cascade = CascadeType.REMOVE 사용
    private List<QuestionAnswer> questionAnswerList;

}
