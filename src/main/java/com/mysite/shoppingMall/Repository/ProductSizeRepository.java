package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
}
