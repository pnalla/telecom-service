package com.blng.telecomservice.service;

import com.blng.telecomservice.api.CustomerPhoneDetailResponse;
import com.blng.telecomservice.exception.DataNotFoundException;
import com.blng.telecomservice.mapper.CustomerPhoneDataMapper;
import com.blng.telecomservice.repository.customer.entity.Customer;
import com.blng.telecomservice.repository.phonedetails.PhoneRepository;
import com.blng.telecomservice.repository.phonedetails.entity.Phone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelecomDataService {

  private final PhoneRepository phoneRepository;
  private final CustomerPhoneDataMapper customerPhoneDataMapper;

  @Value("${spring.telecomService.pageSize:5000}")
  private int pageSize;

  /**
   * This method gets all the phone numbers in db.
   * @return phoneList.
   */
  public List<String> getAllPhoneNumbers() {
    int pagNbr = 0;
    Slice<String> phoneSlice;
    List<String> phoneList = new ArrayList<>();
    do {
      PageRequest pageRequest = PageRequest.of(pagNbr, pageSize);
      phoneSlice = phoneRepository.getAllPhoneNumbers(pageRequest);
      pagNbr += 1;

      if (phoneSlice != null) {
        phoneList.addAll(phoneSlice.stream().collect(Collectors.toList()));
      }
    } while (phoneSlice != null && phoneSlice.hasNext());

    if (CollectionUtils.isEmpty(phoneList)) {
      throw new DataNotFoundException("No data found");
    }

    return phoneList;
  }

  /**
   * This method gets phone details of a customer from db.
   * @param customer Customer.
   * @return response.
   */
  public CustomerPhoneDetailResponse getPhoneDetailsForCustomer(Customer customer) {
    List<Phone> phoneList = phoneRepository.findByCustomer(customer);
    if (CollectionUtils.isEmpty(phoneList)) {
      throw new DataNotFoundException("No data found for this customer");
    }
    CustomerPhoneDetailResponse customerPhoneDetailResponse = new CustomerPhoneDetailResponse();
    customerPhoneDetailResponse.setCustomerId(customer.getCustomerId());
    customerPhoneDetailResponse.setPhoneList(customerPhoneDataMapper.mapPhones(phoneList));
    return customerPhoneDetailResponse;
  }

  /**
   * This method activates and saves a phone number in db.
   * @param phoneNumber String.
   */
  @Transactional
  public void activeCustomerPhoneNumber(String phoneNumber) {
    Phone phone = phoneRepository.findByPhoneNumber(phoneNumber);
    if (!Optional.ofNullable(phone).isPresent()) {
      throw new DataNotFoundException("No data found against this phone number");
    }
    phone.setActive(true);
    phoneRepository.save(phone);
    // long value
  }

}
