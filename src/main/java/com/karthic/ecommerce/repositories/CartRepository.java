package com.karthic.ecommerce.repositories;

import com.karthic.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
