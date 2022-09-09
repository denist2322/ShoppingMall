package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    List<ShoppingCart> findByMallUserId(Integer userId);

    boolean existsByProductIdAndCartColor(long productId, String orderColor);

    ShoppingCart findByProductIdAndCartColor(long productId, String orderColor);

    List<ShoppingCart> findByMallUserIdAndChecked(Integer userId, Integer i);
}
