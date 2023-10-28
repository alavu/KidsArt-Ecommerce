package com.kidsart.library.repository;

import com.kidsart.library.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {
   /* @Query("select od.product.id,od.product.name,sum(od.quantity),sum(od.totalPrice)" +
            " from OrderDetails od where od.order.orderStatus='Cancelled' group by od.product.id,MONTH (od.order.orderDate)")
    List<Object[]> findCancelReportByMonth();*/
}
