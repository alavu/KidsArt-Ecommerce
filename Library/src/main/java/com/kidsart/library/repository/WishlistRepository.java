package com.kidsart.library.repository;

import com.kidsart.library.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    List<Wishlist> findAllByCustomerId(long id);
//    @Query("select exists (select w FROM Wishlist w WHERE w.product.id = :productId AND w.product.id=:productId)")
//    boolean findByProductIdAndCustomerId(@Param("productId") long productId,@Param("customerId") long customerId);

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END FROM Wishlist w WHERE w.product.id = :productId AND w.customer.id = :customerId")
    boolean findByProductIdAndCustomerId(@Param("productId") long productId,@Param("customerId") long customerId);
    Wishlist findById(long id);


}
