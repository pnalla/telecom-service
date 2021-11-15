package com.blng.telecomservice.repository.customer;

import com.blng.telecomservice.repository.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

  List<Customer> findAllByCustomerId(String customerId);
}
