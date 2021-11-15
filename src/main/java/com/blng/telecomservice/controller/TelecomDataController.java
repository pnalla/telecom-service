package com.blng.telecomservice.controller;

import com.blng.telecomservice.api.CustomerPhoneDetailResponse;
import com.blng.telecomservice.repository.customer.entity.Customer;
import com.blng.telecomservice.service.TelecomDataService;
import com.blng.telecomservice.versioning.RequestVersions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class TelecomDataController {
  private final TelecomDataService telecomDataService;

  @GetMapping("/v{version}/phones")
  @RequestVersions(supportedVersions = { 1 }, togglePropertyPrefix = "toggle.phones")
  public ResponseEntity<List<String>> getAllPhoneNumber(
          @PathVariable final int version,
          @RequestHeader final HttpHeaders requestHeaders) {
    return new ResponseEntity<>(telecomDataService.getAllPhoneNumbers(), HttpStatus.OK);
  }

  @GetMapping("/v{version}/customer/{customerId}/phones")
  @RequestVersions(supportedVersions = { 1 }, togglePropertyPrefix = "toggle.customerPhones")
  public ResponseEntity<CustomerPhoneDetailResponse> getPhonesForCustomer(
          @PathVariable final int version,
          @PathVariable @NotBlank final String customerId,
          @RequestHeader final HttpHeaders requestHeaders) {
    Customer customer = new Customer();
    customer.setCustomerId(customerId);
    return new ResponseEntity<>(telecomDataService.getPhoneDetailsForCustomer(customer), HttpStatus.OK);
  }

  @PutMapping("/v{version}/phone/{phoneNumber}/active")
  @RequestVersions(supportedVersions = {1}, togglePropertyPrefix = "toggle.activatePhone")
  public ResponseEntity activatePhoneNumber(
          @PathVariable final int version,
          @PathVariable @NotBlank final String phoneNumber,
          @RequestHeader final HttpHeaders requestHeaders) {
    telecomDataService.activeCustomerPhoneNumber(phoneNumber);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
