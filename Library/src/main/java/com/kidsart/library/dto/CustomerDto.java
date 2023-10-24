package com.kidsart.library.dto;

import com.kidsart.library.model.Address;
import com.kidsart.library.model.City;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {
    private Long id;
//    @NotBlank
//    @Size(min = 3, max = 10, message = "First name contains 3-10 characters")
    private String firstName;
//    @NotBlank
//    @Size(min = 3, max = 10, message = "Last name contains 3-10 characters")
    private String lastName;
    @Email
    private String username;
//    @Size(min = 3, max = 15, message = "Password contains 3-10 characters")
//    @NotBlank(message = "Password is required")
    private String password;

//    @NotBlank
//    @Size(min = 10, max = 15, message = "Phone number contains 10-15 characters")
    private String phoneNumber;

    private List<Address> address;
//    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;
    private City city;
    private String image;
    private String country;
    private boolean isBlocked;

}
