package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Domain.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {
    List<OrderSheet> findByMallUserId(Integer userId);

    List<OrderSheet> findByNowState(int i);
}
