package com.blng.telecomservice.repository.phonedetails.entity;

import com.blng.telecomservice.repository.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity(name = "phone")
@Table(name = "phone")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "phoneid", unique = true, nullable = false)
  @NotBlank
  private String phoneId;

  @Column(name = "phonenumber", unique = true)
  @NotBlank
  private String phoneNumber;

  @Column(name = "phonetype")
  @NotBlank
  private String phoneType;

  @Column(name = "isactive")
  @NotBlank
  private boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customerid", nullable = false)
  private Customer customer;
}
