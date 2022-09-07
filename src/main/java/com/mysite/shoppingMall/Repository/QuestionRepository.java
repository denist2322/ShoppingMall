package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("select q from Question q where subject like %:kw% or content like %:kw%")
    Page<Question> findBySubjectAndContent(@Param("kw") String kw, Pageable pageable);

    List<Question> findByMallUserId(Integer userId);
    Page<Question> findAll(Pageable pageable); // pageable 객체를 입력으로 받아 Page<Question> 타입 객체를 리턴하는 findAll 메서드를 생성


}
