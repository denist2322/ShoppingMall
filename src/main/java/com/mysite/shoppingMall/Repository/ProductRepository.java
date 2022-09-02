package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByTitle(String title);

    Optional<List<Product>> findByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.title LIKE %:kw% OR p.body LIKE %:kw%" )
    List<Product> findByTitleAndBody(@Param("kw")String keyword);
}
