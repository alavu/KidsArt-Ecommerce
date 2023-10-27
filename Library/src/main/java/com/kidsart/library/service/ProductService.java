package com.kidsart.library.service;

import com.kidsart.library.dto.ProductDto;
import com.kidsart.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> findAll();

    List<ProductDto> products();

    List<ProductDto> allProduct();

    Product save(List<MultipartFile> imageProduct, ProductDto product);

    Product update(List<MultipartFile> imageProduct, ProductDto productDto);

    void enableById(Long id);

    void deleteById(Long id);
    /* Soft delete
    void softDeleteProduct(Long id);
    */
    ProductDto getById(Long id);

    Product findById(Long id);


    List<ProductDto> randomProduct();

    Page<ProductDto> searchProducts(int pageNo,String keyword);


    Page<ProductDto> getAllProducts(int pageNo);

    List<ProductDto> findAllProducts();

    Page<ProductDto> getAllProductsForCustomer(int pageNo);


    List<ProductDto> findAllByCategory(String category);


    List<ProductDto> filterHighProducts();

    List<ProductDto> filterLowerProducts();

    List<ProductDto> listViewProducts();

    List<ProductDto> findByCategoryId(Long id);

    Page<ProductDto> findAllByActivated(long id, int pageNo);

    Page<ProductDto> findAllByActivated(int pageNo,String sort);

    List<Map<String,Object>> salesReport();

    Long countAllProducts();

    List<Object[]> getProductStats();

    List<Object[]> getProductsStatsBetweenDates(Date startDate, Date endDate);


}
