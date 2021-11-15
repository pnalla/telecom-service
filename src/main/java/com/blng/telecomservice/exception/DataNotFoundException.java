package com.blng.telecomservice.exception;

import com.blng.telecomservice.api.ApiError;
import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {
  private ApiError apiError;
  public DataNotFoundException(String msg) {
    super(msg);
    this.apiError = ApiError.builder().errorId("API-400").message(msg).build();
  }
}
