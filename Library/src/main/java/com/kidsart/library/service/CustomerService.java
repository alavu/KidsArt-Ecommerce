package com.kidsart.library.service;

import com.kidsart.library.dto.CustomerDto;
import com.kidsart.library.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer update(CustomerDto customerDto);
    Customer findById(long id);
    List<CustomerDto> findAll();
    void blockById(Long id);

    void unblockById(Long id);

    CustomerDto getCustomer(String username);
    //    void disable(long id);
//
////    void enable(long id);
    CustomerDto findByEmailCustomerDto(String email);
//
//    CustomerDto updateAccount(CustomerDto customerDto,String email);

    void changePass(CustomerDto customerDto);
}

