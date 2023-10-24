package com.kidsart.library.service.Impl;

import com.kidsart.library.dto.CustomerDto;
import com.kidsart.library.model.Customer;
import com.kidsart.library.repository.CustomerRepository;
import com.kidsart.library.repository.RoleRepository;
import com.kidsart.library.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPassword(customerDto.getPassword());
        customer.setUsername(customerDto.getUsername());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public CustomerDto getCustomer(String username) {
        CustomerDto customerDto = new CustomerDto();
        Customer customer = customerRepository.findByUsername(username);
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

    @Override
    public CustomerDto findByEmailCustomerDto(String email) {
        Customer customer = customerRepository.findByUsername(email);
        CustomerDto customerDto=new CustomerDto();
        customerDto.setUsername(customer.getUsername());
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPassword(customer.getPassword());
        customerDto.setBlocked(customer.is_blocked());

        return customerDto;
    }

//    @Override
//    public void disable(long id) {
//        Customer customer=findById(id);
//        customer.set_blocked(false);
//        customerRepository.save(customer);
//    }
//
//    @Override
//    public void enable(long id) {
//        Customer customer = findById(id);
//        customer.set_blocked(true);
//        customerRepository.save(customer);
//    }

    @Override
    public void changePass(CustomerDto customerDto) {
        Customer customer=customerRepository.findByUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customerRepository.save(customer);
    }


    @Override
    public Customer update(CustomerDto dto) {
        Customer customer = customerRepository.findByUsername(dto.getUsername());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setUsername(dto.getUsername());
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<CustomerDto> findAll() {
        List<CustomerDto>  customerDtoList = new ArrayList<>();
        List<Customer> customers = customerRepository.findAll();
        for(Customer customer:customers){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setFirstName(customer.getFirstName());
            customerDto.setLastName(customer.getLastName());
            customerDto.setUsername(customer.getUsername());
            customerDto.setPhoneNumber(customer.getPhoneNumber());
            customerDto.setBlocked(customer.is_blocked());
            customerDtoList.add(customerDto);
        }
        return customerDtoList;
    }



//    @Override
//    public void blockUser(Long id) {
//        Customer customer = customerRepository.getReferenceById(id);
//        if (customer.is_blocked()){
//            customer.set_blocked(false);
//        }
//        else{
//            customer.set_blocked(true);
//        }
//    }
@Override
public void blockById(Long id) {

    Customer customer=customerRepository.getReferenceById(id);
    customer.set_blocked(true);
    customerRepository.save(customer);


}

    @Override
    public void unblockById(Long id) {

        Customer customer=customerRepository.getReferenceById(id);
        customer.set_blocked(false);
        customerRepository.save(customer);

    }


}
