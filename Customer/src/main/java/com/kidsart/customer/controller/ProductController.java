package com.kidsart.customer.controller;

import com.kidsart.library.dto.ProductDto;
import com.kidsart.library.model.Category;
import com.kidsart.library.service.BannerService;
import com.kidsart.library.service.CategoryService;
import com.kidsart.library.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final CategoryService categoryService;

    private BannerService bannerService;

    @GetMapping("/product-list")
    public String productList(@RequestParam(name = "id",required = false,defaultValue = "0") long id, Model model,
                              @RequestParam(name = "pageNo",required = false,defaultValue = "0")int pageNo,
                              @RequestParam(name = "sort",required = false,defaultValue = "")String sort) {
        model.addAttribute("page", "Products");
        model.addAttribute("title", "Menu");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        Page<ProductDto> products;
        if(id==0) {
            products = productService.findAllByActivated(pageNo,sort);
            model.addAttribute("sort",sort);
        }else{
            products = productService.findAllByActivated(id,pageNo);
        }
        long totalProducts = products.getTotalElements();
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("size",products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "shop-list";
    }

    @GetMapping("/search-products/{pageNo}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam(name = "keyword") String keyword,
                                Model model
    ) {
        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
        long totalProducts = products.getTotalElements();
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "shop-list";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.getById(id);
//        List<ProductDto> productDtoList = productService.findAllByCategory(product.getCategory().getName());
        model.addAttribute("product", product);
        model.addAttribute("title", "Product Detail");
        model.addAttribute("page", "Product Detail");
        model.addAttribute("productDetail", product);
        return "product-detail";
    }

    @GetMapping("/menu")
    public String Menu(Model model) {
        model.addAttribute("page", "Products");
        model.addAttribute("title", "Menu");
//        List<Category> categories = categoryService.findAllByActivatedTrue();
        List<ProductDto> products = productService.products();
        model.addAttribute("products", products);
//        model.addAttribute("categories", categories);
        return "index";

    }
}
