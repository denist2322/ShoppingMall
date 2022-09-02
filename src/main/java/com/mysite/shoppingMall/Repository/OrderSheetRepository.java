package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Entity.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {
    List<OrderSheet> findByMallUserId(Integer userId);

    List<OrderSheet> findByNowStateAndMallUserId(int i, int userId);
}
