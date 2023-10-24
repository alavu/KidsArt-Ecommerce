package com.kidsart.library.repository;

import com.kidsart.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
    @Query("select p from Product p where p.is_deleted = false and p.is_activated = true")
    List<Product> getAllProduct();

    @Query(value = "select * from products where is_activated = true and category_id = :id", nativeQuery = true)
    List<Product> findAllByActivatedTrue(@Param("id") long id);

    @Query(value = "select * from products where is_activated = true", nativeQuery = true)
    List<Product> findAllByActivatedTrue();

    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> findAllByNameOrDescription(String keyword);


    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1 and p.is_activated = true and p.is_deleted = false")
    List<Product> findAllByCategory(String category);

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from products p where p.is_activated = true and p.is_deleted = false order by rand() limit 9", nativeQuery = true)
    List<Product> randomProduct();

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from products p where p.is_deleted = false and p.is_activated = true order by p.cost_price desc limit 9", nativeQuery = true)
    List<Product> filterHighProducts();

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from products p where p.is_deleted = false and p.is_activated = true order by p.cost_price asc limit 9", nativeQuery = true)
    List<Product> filterLowerProducts();


    @Query(value = "select p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted from products p where p.is_deleted = false and p.is_activated = true limit 4", nativeQuery = true)
    List<Product> listViewProduct();


    @Query(value = "select p from Product p inner join Category c on c.id = ?1 and p.category.id = ?1 where p.is_activated = true and p.is_deleted = false")
    List<Product> getProductByCategoryId(Long id);

    @Query(value = "SELECT * FROM products WHERE is_activated = true ORDER BY CASE WHEN :sort = 'lowToHigh' THEN cost_price END ASC, CASE WHEN :sort = 'highToLow' THEN cost_price END DESC", nativeQuery = true)
    List<Product> findAllByActivatedTrueAndSortBy(@Param("sort") String sort);

/*    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> searchProducts(String keyword);
    */
    List<Product> findAllByNameStartingWithIgnoreCase(String keyword);

    @Query(value = "select count(*) from Product")
    Long CountAllProducts();

    @Query(value = "SELECT p.product_id, p.name, c.name, " +
            "SUM(od.quantity) AS total_quantity_ordered, SUM(od.quantity * p.cost_Price) AS total_revenue " +
            "FROM products p " +
            "JOIN order_Detail od ON p.product_id = od.product_id " +
            "JOIN orders o ON od.order_id = o.order_id " +
            "JOIN categories c ON p.category_id = c.category_id " +
            "WHERE o.order_Status = 'Delivered' " +
            "GROUP BY p.product_id, p.name, c.name " +
            "ORDER BY total_revenue DESC",nativeQuery = true)
    List<Object[]> getProductStatsForConfirmedOrders();

    @Query(value = "SELECT p.product_id, p.name, c.name, " +
            "SUM(od.quantity) AS total_quantity_ordered, SUM(od.quantity * p.cost_Price) AS total_revenue " +
            "FROM products p " +
            "JOIN order_Detail od ON p.product_id = od.product_id " +
            "JOIN orders o ON od.order_id = o.order_id " +
            "JOIN categories c ON p.category_id = c.category_id " +
            "WHERE o.order_Status = 'Delivered' " +
            "AND o.order_date BETWEEN :startDate AND :endDate " +
            "GROUP BY p.product_id, p.name, c.name " +
            "ORDER BY total_revenue DESC",nativeQuery = true)
    List<Object[]> getProductsStatsForConfirmedOrdersBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT p.name, p.currentQuantity FROM Product p WHERE p.is_deleted = false AND p.is_activated = true")
    List<Object[]> stockReport();
}

