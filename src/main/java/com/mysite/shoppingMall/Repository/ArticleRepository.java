package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
