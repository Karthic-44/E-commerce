package com.karthic.ecommerce.service;

import com.karthic.ecommerce.payload.CartDTO;
import org.springframework.stereotype.Service;


public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);



}
