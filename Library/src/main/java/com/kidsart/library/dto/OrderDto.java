package com.kidsart.library.dto;

import com.kidsart.library.model.Customer;
import com.kidsart.library.model.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Date orderDate;
    private Date deliveyDate;
    private double totalPrice;
    private double shippingFee;
    private String notes;
    private String orderStatus;
    private String paymentMethod;
    private boolean isAccept;
    private AddressDto address;
    private Customer customer;
    private List<OrderDetails> orderDetails;

}
