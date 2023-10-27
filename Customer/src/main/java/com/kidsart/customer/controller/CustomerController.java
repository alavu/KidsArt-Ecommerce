package com.kidsart.customer.controller;

import com.kidsart.library.dto.AddressDto;
import com.kidsart.library.dto.CustomerDto;
import com.kidsart.library.model.*;
import com.kidsart.library.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AddressService addressService;
    private OrderService orderService;

    @GetMapping("/profile")

    public String getMyAccount( @RequestParam(required = false)String tab, Model model, Principal principal, HttpSession session){
            if (principal == null) {
                return "redirect:/login";
            } else {
                Customer customer = customerService.findByUsername(principal.getName());
                session.setAttribute("userLoggedIn", true);
                session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
                if (tab != null && !tab.isEmpty()) {
                    model.addAttribute("openTab", tab);
                    System.out.println(tab);
                } else {
                    model.addAttribute("openTab", "");
                }
                List<Address> address = customer.getAddress();
                model.addAttribute("customer", customer);
                model.addAttribute("title", "Dashboard");
                model.addAttribute("addressDto",new AddressDto());
                model.addAttribute("addressList",address);
                return "customer-information";
            }
        }

    @GetMapping("/about")
    public String getAboutUs(){
        return "about";
    }

    @GetMapping("/contact")
    public String getContactUs(){
        return "contact";
    }

    @GetMapping("/address")
    public String getAddress(Principal principal,
                             Model model){
        if(principal==null){
            return "redirect:/login";
        }else{
            Customer customer = customerService.findByUsername(principal.getName());
            List<Address> address = customer.getAddress();
            model.addAttribute("size",address.size());
            model.addAttribute("addressDto",new AddressDto());
            model.addAttribute("addressList",address);

            return "redirect:/profile?tab=address";
        }
    }

    @PostMapping("/add-address")
    public String getAddAddress(@ModelAttribute("addressDto")AddressDto addressDto, BindingResult result,
                                Model model,Principal principal){
        if (result.hasErrors()) {
            model.addAttribute("error", "Validation error");
            return "customer-information";
        }
        addressService.save(addressDto, principal.getName());
        model.addAttribute("success","Address Added");
        System.out.println("Add address Clicked!!!!!!!!!!!");
        return "redirect:/profile?tab=address";
    }

    @PostMapping("/add-address-checkout")
    public String AddAddress(@ModelAttribute("addressDto")AddressDto addressDto,
                             Model model,Principal principal,HttpServletRequest request){

        addressService.save(addressDto, principal.getName());
        model.addAttribute("success","Address Added");

        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/update-address/{id}")
    public String getUpdateAddress(@PathVariable("id")Long address_Id,Model model,Principal principal){
        if(principal==null){
            return "redirect:/login";
        }
        AddressDto addressDto=addressService.findById(address_Id);
        model.addAttribute("addressDto",addressDto);
        return"update-address";
    }

    @PostMapping("/update-address/{id}")
    public String updateAddress(@Valid@ModelAttribute("addressDto")AddressDto addressDto,Model model,
                                BindingResult result){
        if (result.hasErrors()) {
            model.addAttribute("addressDto", addressDto);
            return "update-address";
        }
        addressService.update(addressDto);
        model.addAttribute("success","Address updated");

        return "redirect:/profile?tab=address";

    }
//* check and verify the implimetation is working or not
    @GetMapping("/edit-address")
    public String editAddress(Address address,RedirectAttributes attributes,Principal principal){
        try {
            if(principal!=null){
                System.out.println("This is my address is"+address.getId());
                addressService.updateAddress(address, principal.getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/profile";
    }

    @GetMapping("/enable-address/{id}")
    public String enableAddress(@PathVariable("id")long address_id,
                                RedirectAttributes redirectAttributes){

        addressService.enable(address_id);
        redirectAttributes.addFlashAttribute("success","Address enabled");

        return "redirect:/profile?tab=address";
    }

    @GetMapping("/disable-address/{id}")
    public String disableAddress(@PathVariable("id")long address_id,
                                 RedirectAttributes redirectAttributes){

        addressService.disable(address_id);
        redirectAttributes.addFlashAttribute("success","Address disabled");

        return "redirect:/profile?tab=address";
    }

    //   Hard delete
//    @GetMapping("/delete-address/{id}")
//    public String deleteAddress(@PathVariable("id")Long address_id,Model model){
//
//        addressService.deleteAddress(address_id);
//        model.addAttribute("success","Address Deleted");
//        return "redirect:/profile?tab=address";
//    }
    // Soft Delete
    @GetMapping("/delete-address/{id}")
    public String deleteAddress(@PathVariable("id") Long address_id, Model model) {
        addressService.disableAddress(address_id);
        model.addAttribute("success","Address Deleted");
        return "redirect:/profile?tab=address";
    }
    @GetMapping("/account-details")
    public String getUpdateAccount(Principal principal,Model model){

        if(principal==null){
            return "redirect:/login";
        }else{
            CustomerDto customer = customerService.findByEmailCustomerDto(principal.getName());
            model.addAttribute("customer",customer);
            return "customer-information";
        }
    }

    @PostMapping("/update-profile")
    public String UpdateAccount(@ModelAttribute("customer")CustomerDto customerDto,
                                RedirectAttributes redirectAttributes,
                                Principal principal){

        if(principal==null){
            return "redirect:/login";
        }else{

            Customer customerUpdated = customerService.update(customerDto);
            redirectAttributes.addFlashAttribute("customer",customerUpdated);
            redirectAttributes.addFlashAttribute("success","Updated Successfully");
            return "redirect:/profile?tab=account-detail";

        }
    }


    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("repeatNewPassword") String repeatPassword,
                             RedirectAttributes attributes,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {

            CustomerDto customer = customerService.findByEmailCustomerDto(principal.getName());
            if (passwordEncoder.matches(oldPassword, customer.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, customer.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 3) {

                customer.setPassword(passwordEncoder.encode(newPassword));
                customerService.changePass(customer);
                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile?tab=account-detail";
            } else {

                attributes.addFlashAttribute("message", "Entered Password Does Not Match");
                return "redirect:/profile?tab=account-detail";
            }
        }
    }
}

