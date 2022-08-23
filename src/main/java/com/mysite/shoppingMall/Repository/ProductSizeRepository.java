package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Domain.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
}
