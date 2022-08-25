package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Domain.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {
}
