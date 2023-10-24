package com.kidsart.library.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.List;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
//@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE product_id = ?")
//@Where(clause = "is_deleted = false")
public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_id")
        private Long id;
        private String name;
        @Column(columnDefinition = "TEXT")
        private String description;
        private int currentQuantity;
        private double costPrice;
        private double salePrice;
        @OneToMany(mappedBy= "product",cascade =CascadeType.ALL)
        private List<CartItem> cartItems;
        @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
        private List<Image> images;
        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinColumn(name = "category_id", referencedColumnName = "category_id")
        private Category category;
        @OneToMany(mappedBy = "product")
        private List<OrderDetails> orderDetails;
        private boolean is_activated;
        private boolean is_deleted;
//        private boolean is_deleted = Boolean.FALSE;

    }
