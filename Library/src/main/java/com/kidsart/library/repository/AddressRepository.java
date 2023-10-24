package com.kidsart.library.repository;

import com.kidsart.library.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    Address findById(long id);

    @Query(value = "select * from address where is_default = true and customer_id = :id", nativeQuery = true)
    Address findByActivatedTrue(@Param("id") long id);


    @Query(value = "select * from Address where enabled = false and customer_id = :id", nativeQuery = true)
    List<Address> findByEnabledFalse(@Param("id") long id);

//    @Query(value = "SELECT * FROM address WHERE customer_id = :customerId ORDER BY RAND() LIMIT 1", nativeQuery = true)
//    Address findRandomAddressByCustomerId(Long customerId);

}
