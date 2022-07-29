package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Vo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
