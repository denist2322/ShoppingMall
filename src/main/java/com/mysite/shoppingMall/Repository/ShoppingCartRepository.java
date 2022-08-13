package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Vo.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
    List<ShoppingCart> findByMallUserId(Integer userId);

    boolean existsByProductIdAndCartColor(long productId, String orderColor);

    ShoppingCart findByProductIdAndCartColor(long productId, String orderColor);
}
