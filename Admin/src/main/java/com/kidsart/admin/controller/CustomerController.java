package com.kidsart.admin.controller;

import com.kidsart.library.dto.CustomerDto;
import com.kidsart.library.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequiredArgsConstructor
public class CustomerController {
    private CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/users")
  public String listUser(Model model, Principal principal) {
      if (principal == null) {
          return "login";
      }
      List<CustomerDto> customers = new ArrayList<>();
      customers = customerService.findAll();
      model.addAttribute("title", "users");
      model.addAttribute("users", customers);
      model.addAttribute("size", customers.size());
      return "users";
  }
//  public String blockUser(@PathVariable("id") Long id) {
//      try{
//          customerService.blockById(id);
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//      return "redirect:/users";
//  }

    @RequestMapping(value="/unblock-users/{id}", method={RequestMethod.GET})
    public String unblockUser(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            customerService.unblockById(id);
            attributes.addFlashAttribute("success", "Unblocked successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Failed to Unblock");
        }
        return "redirect:/users";
    }


    @RequestMapping(value="/block-users/{id}", method={RequestMethod.GET})
    public String blockUser(@PathVariable("id") Long id, RedirectAttributes attributes){

        try {
            customerService.blockById(id);
            attributes.addFlashAttribute("success", "Blocked successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Failed to delete");
        }
        return "redirect:/users";
    }
}
