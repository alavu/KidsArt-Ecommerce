package com.kidsart.admin.controller;

import com.kidsart.library.dto.BannerDto;
import com.kidsart.library.dto.ProductDto;
import com.kidsart.library.service.BannerService;
import com.kidsart.library.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class BannerController {

    private BannerService bannerService;

    private ProductService productService;

    public BannerController(BannerService bannerService, ProductService productService) {
        this.bannerService = bannerService;
        this.productService = productService;
    }

    @GetMapping("/banners")
    public String getCoupon(Principal principal, Model model){
        if(principal==null){
            return "redirect:/login";
        }
        List<BannerDto> bannerDtoList=bannerService.getAllBanners();
        model.addAttribute("banners",bannerDtoList);
        model.addAttribute("size",bannerDtoList.size());

        return "banners";
    }

    @GetMapping("/banners/add-banner")
    public String getAddOffer(Principal principal, Model model){

        if(principal == null){
            return "redirect:/login";
        }

        List<ProductDto> productList = productService.findAllProducts();

        model.addAttribute("products",productList);
        model.addAttribute("banner",new BannerDto());

        return "add-banner";
    }

    @PostMapping("/banners/save")
    public String saveBanner(@RequestParam("bannerImage") MultipartFile bannerImage,
                             @ModelAttribute("banner")BannerDto bannerDto, RedirectAttributes redirectAttributes
                             ){

        try{
            bannerService.save(bannerDto,bannerImage);
            redirectAttributes.addFlashAttribute("success", "Added new Banner successfully!");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new Banner!");
        }


        return "redirect:/banners";
    }



    @GetMapping("/banners/update-banner/{id}")
    public String updateBannerForm(@PathVariable("id") long id, Model model, Principal principal){

        if(principal==null){
            return "redirect:/login";
        }
        List<ProductDto> productList = productService.findAllProducts();
        BannerDto bannerDto=bannerService.findById(id);
        model.addAttribute("banner",bannerDto);
        model.addAttribute("products",productList);



        return "update-banner";
    }


    @PostMapping("/banners/update-banner/{id}")
    public String updateBanner(@ModelAttribute("banner") BannerDto bannerDto,
                               @RequestParam("bannerImage") MultipartFile bannerImage,
                               RedirectAttributes redirectAttributes){

        try {
            bannerService.update(bannerDto,bannerImage);
            redirectAttributes.addFlashAttribute("success", "Updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/banners";
    }


    @GetMapping("/disable-banner/{id}")
    public String disable(@PathVariable("id")long id,RedirectAttributes redirectAttributes){

        bannerService.disable(id);
        redirectAttributes.addFlashAttribute("success","Banner Disabled");
        return "redirect:/banners";
    }

    @GetMapping("/enable-banner/{id}")
    public String enable(@PathVariable("id")long id, RedirectAttributes redirectAttributes){


        bannerService.enable(id);

        redirectAttributes.addFlashAttribute("success","Banner Enabled");
        return "redirect:/banners";
    }

    @GetMapping("/delete-banner/{id}")
    public String delete(@PathVariable("id")long id,RedirectAttributes redirectAttributes){

        bannerService.deleteBanner(id);

        redirectAttributes.addFlashAttribute("success","Banner deleted");

        return "redirect:/banners";
    }



}
