package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Vo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByTitle(String title);
}
