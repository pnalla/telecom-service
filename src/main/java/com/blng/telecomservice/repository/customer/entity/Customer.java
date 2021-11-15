package com.blng.telecomservice.repository.customer.entity;

import com.blng.telecomservice.repository.phonedetails.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "customer")
@Table(name = "customer")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
  @Id
  @Column(name = "customerid", unique = true, nullable = false)
  @NotBlank
  private String customerId;

  @Column(name = "customername")
  @NotBlank
  private String customerName;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Phone> phoneList;

}
