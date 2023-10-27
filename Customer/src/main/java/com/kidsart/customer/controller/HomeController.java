package com.kidsart.customer.controller;

import com.kidsart.library.dto.BannerDto;
import com.kidsart.library.dto.ProductDto;
import com.kidsart.library.model.Category;
import com.kidsart.library.model.Customer;
import com.kidsart.library.model.Product;
import com.kidsart.library.service.BannerService;
import com.kidsart.library.service.CategoryService;
import com.kidsart.library.service.CustomerService;
import com.kidsart.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BannerService bannerService;


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        /*  if (principal == null) {
            return "redirect:/login";
        } else {
        */
            List<BannerDto> bannerDtoList=bannerService.getAllBanners();
            List<Category> categories = categoryService.findAllByActivatedTrue();
            if (principal != null) {
                Customer customer = customerService.findByUsername(principal.getName());
                session.setAttribute("userLoggedIn", true);
                session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
            }
            model.addAttribute("page", "Products");
            model.addAttribute("title", "Menu");
            List<ProductDto> products = productService.products();
            model.addAttribute("categories",categories);
            model.addAttribute("banners",bannerDtoList);
            model.addAttribute("products", products);
            return "index";
    }
}
