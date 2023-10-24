package com.kidsart.library.service.Impl;

import com.kidsart.library.dto.AddressDto;
import com.kidsart.library.model.Address;
import com.kidsart.library.model.Customer;
import com.kidsart.library.repository.AddressRepository;
import com.kidsart.library.repository.CustomerRepository;
import com.kidsart.library.service.AddressService;
import com.kidsart.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    private final CustomerService customerService;
    private final AddressRepository addressRepository;

    private final CustomerRepository customerRepository;


    public AddressServiceImpl(CustomerService customerService, AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Address save(AddressDto addressDto, String username) {

        Customer customer=customerService.findByUsername(username);

        Address address=new Address();

        address.setAddress_line_1(addressDto.getAddress_line_1());
        address.setAddress_line_2(addressDto.getAddress_line_2());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setPincode(addressDto.getPincode());
        address.setCustomer(customer);
        address.set_default(false);


        return addressRepository.save(address);
    }

    @Override
    public Address update(AddressDto addressDto) {
        long id=addressDto.getId();
        Address address=addressRepository.findById(id);
        address.setAddress_line_1(addressDto.getAddress_line_1());
        address.setAddress_line_2(addressDto.getAddress_line_2());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setPincode(addressDto.getPincode());
        return addressRepository.save(address);
    }

    // * New implimetation check the functionality and  verify
    @Override
    public Address updateAddress(Address address,String username) {
        Customer customer=customerRepository.findByUsername(username);
        Address address1=getById(address.getId());
        address1.setAddress_line_1(address.getAddress_line_1());
        address1.setAddress_line_2(address.getAddress_line_2());
        address1.setCity(address.getCity());
        address1.setCountry(address.getCountry());
        address1.setPincode(address.getPincode());
        address1.setCustomer(customer);

        return addressRepository.save(address1);
    }

    @Override
    public Address getById(Long id) {
        return addressRepository.getById(id);
    }


    @Override
    public AddressDto findById(long id) {
        Address address=addressRepository.findById(id);
        AddressDto addressDto=new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setAddress_line_1(address.getAddress_line_1());
        addressDto.setAddress_line_2(address.getAddress_line_2());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setPincode(address.getPincode());
        addressDto.setCustomer(address.getCustomer());
        return addressDto;
    }

//    @Override
//    public Address findAddressByCustomerId(Long id) {
//        return addressRepository.findRandomAddressByCustomerId(id);
//    }

//    @Override
//    public void deleteAddress(long id) {
//
//        addressRepository.deleteById(id);
//    }

// Soft delete imp
    @Override
    public void disableAddress(long id) {
    Address address = addressRepository.getById(id);
    address.setEnabled(true);
    addressRepository.save(address);
    }

    @Override
    public void enable(long id) {
        Address address=addressRepository.findById(id);
        address.set_default(true);

        addressRepository.save(address);
    }

    @Override
    public void disable(long id) {
        Address address = addressRepository.findById(id);
        address.set_default(false);

        addressRepository.save(address);
    }

    @Override
    public Address findDefaultAddress(long customer_id) {
        return addressRepository.findByActivatedTrue(customer_id);
    }

    @Override
    public Address findByIdOrder(long id) {
        return addressRepository.findById(id);
    }

    @Override
    public List<Address> findByEnabledFalse(long id) {
        return addressRepository.findByEnabledFalse(id);
    }
}
