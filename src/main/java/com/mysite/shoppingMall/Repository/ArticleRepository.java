package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Vo.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
