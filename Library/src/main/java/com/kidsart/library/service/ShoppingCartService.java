package com.kidsart.library.service;

import com.kidsart.library.dto.ProductDto;
import com.kidsart.library.model.ShoppingCart;


public interface ShoppingCartService {

    ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username);

    ShoppingCart updateCart(ProductDto productDto, int quantity, String username);

    ShoppingCart removeItemFromCart(ProductDto productDto, String username);
    ShoppingCart updateTotalPrice(Double newTotalPrice,String username);
    ShoppingCart deleteCartById(Long id);

}
