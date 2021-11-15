package com.blng.telecomservice.mapper;

import com.blng.telecomservice.api.Phone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CustomerPhoneDataMapper {

  public abstract List<Phone> mapPhones(List<com.blng.telecomservice.repository.phonedetails.entity.Phone> phoneList);
}
