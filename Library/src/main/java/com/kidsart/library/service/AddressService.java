package com.kidsart.library.service;
import com.kidsart.library.dto.AddressDto;
import com.kidsart.library.model.Address;

import java.util.List;
import java.util.Map;

public interface AddressService {

    Address save(AddressDto addressDto,String username);

    Address update(AddressDto addressDto);

    Address updateAddress(Address address,String username);
    Address getById(Long id);

    AddressDto findById(long id);

//    void deleteAddress(long id);
    void disableAddress(long id);

    void enable(long id);

    void disable(long id);

//    Address findAddressByCustomerId(Long id);

    Address findDefaultAddress(long customer_id);

    Address findByIdOrder(long id);
    List<Address> findByEnabledFalse(long id);
 }
