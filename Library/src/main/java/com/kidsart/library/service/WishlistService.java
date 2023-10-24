package com.kidsart.library.service;

import com.kidsart.library.model.Customer;
import com.kidsart.library.model.Wishlist;

import java.util.List;

public interface WishlistService {

    List<Wishlist> findAllByCustomer(Customer customer);

    boolean findByProductId(long id,Customer customer);

    Wishlist save(long productId,Customer customer);

    void deleteWishlist(long id);
}
