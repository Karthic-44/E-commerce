package com.karthic.ecommerce.service;

import com.karthic.ecommerce.payload.CartDTO;
import jakarta.transaction.Transactional;

import java.util.List;


public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);


    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);

    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCart(Long cartId, Long productId);
}
