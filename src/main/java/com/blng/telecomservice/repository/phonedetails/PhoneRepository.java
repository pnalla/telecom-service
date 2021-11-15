package com.blng.telecomservice.repository.phonedetails;

import com.blng.telecomservice.repository.customer.entity.Customer;
import com.blng.telecomservice.repository.phonedetails.entity.Phone;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {
  String SELECT_FROM_PHONE = "select phonenumber from phone ";

  List<Phone> findByCustomer(Customer customer);

  Phone findByPhoneNumber(String phoneNumber);

  @Query(nativeQuery = true, value = SELECT_FROM_PHONE)
  Slice<String> getAllPhoneNumbers(Pageable pageable);
}
