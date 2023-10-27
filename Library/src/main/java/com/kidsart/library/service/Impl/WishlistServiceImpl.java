package com.kidsart.library.service.Impl;

import com.kidsart.library.model.Customer;
import com.kidsart.library.model.Product;
import com.kidsart.library.model.Wishlist;
import com.kidsart.library.repository.WishlistRepository;
import com.kidsart.library.service.ProductService;
import com.kidsart.library.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private WishlistRepository wishlistRepository;
    private ProductService productService;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
    }

    @Override
    public List<Wishlist> findAllByCustomer(Customer customer) {
    List<Wishlist> wishLists =wishlistRepository.findAllByCustomerId(customer.getId());
    return wishLists;
    }

    @Override
    public boolean findByProductId(long productId, Customer customer) {
    boolean exists = wishlistRepository.findByProductIdAndCustomerId(productId, customer.getId());
    return exists;
    }

    @Override
    public Wishlist save(long productId, Customer customer) {
        Product product = productService.findById(productId);
        Wishlist wishlist =new Wishlist();
        wishlist.setProduct(product);
        wishlist.setCustomer(customer);
        wishlistRepository.save(wishlist);
        return wishlist;
    }

    @Override
    public void deleteWishlist(long id) {
        Wishlist wishlist=wishlistRepository.findById(id);
        wishlistRepository.delete(wishlist);
    }
}

