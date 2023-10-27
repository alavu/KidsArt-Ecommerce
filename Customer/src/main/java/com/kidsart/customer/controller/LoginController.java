package com.kidsart.customer.controller;

import com.kidsart.library.Exception.OtpSendException;
import com.kidsart.library.dto.CustomerDto;
import com.kidsart.library.model.Customer;
import com.kidsart.library.service.CustomerService;
import com.kidsart.library.service.SmsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final CustomerService customerService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SmsService smsService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        Object attribute = session.getAttribute("userLoggedIn");
        if (attribute != null) {
            return "redirect:/index";
        }
        model.addAttribute("title", "Login Page");
        model.addAttribute("page", "Home");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("page", "Register");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String registerCustomer(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                   BindingResult result,
                                   Model model, HttpSession httpSession) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            String username = customerDto.getUsername();
            // Check if someone with the same email is already registered
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Email already registerd!");
                return "register";
            }

            String otp = smsService.generateOtp();
            smsService.sendOtp(otp);
            httpSession.setAttribute("customerDto", customerDto);
            httpSession.setAttribute("otp", otp);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server is error, try again later!");
        }
        return "otp-verification";
    }

    @PostMapping("/do-register/verify")
    public String verifyOtp(HttpSession session, @RequestParam("inputOtp") String inputOtp, Model model) {
        CustomerDto customerDto = (CustomerDto) session.getAttribute("customerDto");
        String otp = (String) session.getAttribute("otp");
        if (customerDto != null && otp.equals(inputOtp)) {
            // OTP verified, save user details and login user
            customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
            customerService.save(customerDto);
        } else {
            model.addAttribute("error", "OTP is not valid");
            return "otp-verification";
        }

        session.removeAttribute("customerDto");
        session.removeAttribute("otp-verification");
        return "redirect:/login";
    }

    /*Forgot Password implimentation*/

    @GetMapping("/forgot-password")
    public String getForgotPassword(){

        return"forgot-password";
    }

    @PostMapping("/send-otp")
    public String resetPassword(@RequestParam("username")String username,
                                RedirectAttributes redirectAttributes, HttpSession session){

        try {

            CustomerDto customerDto = customerService.findByEmailCustomerDto(username);

            session.setAttribute("customerDto", customerDto);

            String otp = smsService.generateOtp();
            smsService.sendOtp(otp);
            session.setAttribute("OTP", otp);
            redirectAttributes.addAttribute("username", username);
        }catch (OtpSendException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/forgot-password";
        }
        return "redirect:/forgot-password";
    }

    @PostMapping("/verify-inputotp")
    public String VerifyOtpPassword(@RequestParam("inputedOtp")String inputOtp,
                                    RedirectAttributes redirectAttributes,
                                    HttpSession session){

        String otp=(String)session.getAttribute("OTP");

        if(otp.equals(inputOtp)){
            session.removeAttribute("OTP");
            return "reset-password";
        }else{
            redirectAttributes.addFlashAttribute("error","Otp is invalid");
            return "redirect:/forgot-password";
        }

    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("password")String password,HttpSession session, Model model){

        CustomerDto customerDto=(CustomerDto)session.getAttribute("customerDto");

        customerDto.setPassword(passwordEncoder.encode(password));
        customerService.update(customerDto);
        session.removeAttribute("customerDto");
        return "redirect:/login";
    }
}