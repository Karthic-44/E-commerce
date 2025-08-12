package com.karthic.ecommerce.service;

import com.karthic.ecommerce.payload.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);



}
