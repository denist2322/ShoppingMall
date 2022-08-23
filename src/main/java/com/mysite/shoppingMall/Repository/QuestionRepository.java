package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
