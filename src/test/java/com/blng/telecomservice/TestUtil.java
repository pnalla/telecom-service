package com.blng.telecomservice;

import com.blng.telecomservice.repository.customer.entity.Customer;
import com.blng.telecomservice.repository.phonedetails.entity.Phone;

import java.util.Arrays;
import java.util.List;

public class TestUtil {
  public static List<String> getAllPhoneNumbers() {
    return Arrays.asList("9123456789",
            "9123456788","9123456780");
  }

  public static List<Phone> getPhoneForCustomer() {
    return Arrays.asList(Phone.builder()
            .customer(Customer.builder().customerId("12345678").build())
            .phoneNumber("9123456789").build(),
            Phone.builder()
                    .customer(Customer.builder().customerId("12345678").build())
                    .phoneNumber("9123456788").build());
  }

  public static List<com.blng.telecomservice.api.Phone> getPhoneApiList() {
    return Arrays.asList(com.blng.telecomservice.api.Phone.builder()
            .phoneNumber("9123456789")
            .phoneType("work").build(),
            com.blng.telecomservice.api.Phone.builder()
                    .phoneNumber("9123456788")
                    .phoneType("work").build());
  }


}
