package com.karthic.ecommerce.repositories;


import com.karthic.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
