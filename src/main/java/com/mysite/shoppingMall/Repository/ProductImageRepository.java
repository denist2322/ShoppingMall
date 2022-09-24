package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
