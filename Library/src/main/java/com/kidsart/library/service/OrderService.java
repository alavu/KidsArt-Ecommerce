package com.kidsart.library.service;

import com.kidsart.library.dto.MonthlyCancelReportDTO;
import com.kidsart.library.model.Customer;
import com.kidsart.library.model.Order;
import com.kidsart.library.model.ShoppingCart;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface OrderService {

    Order save(ShoppingCart cart, long address_id, String paymentMethod, Double oldTotalPrice);

    void cancelOrder(long order_id);

    List<Order> findAllOrders();

    void acceptOrder(long id);

    Order findOrderById(long id);

    List<Order> findAllOrdersByCustomer(long id);

    List<Long> findAllOrderCountForEachMonth();

    Double getTotalOrderAmount();

    Long countTotalConfirmedOrders();

    Double getTotalAmountForMonth();

    List<Double> getTotalAmountForEachMonth();

    List<MonthlyCancelReportDTO> getMonthlyCancelReport();

    void updatePayment(Order order, boolean status);

    void updateOrderStatus(String status, long order_id);

    void returnOrder(long id, Customer customer);

}
