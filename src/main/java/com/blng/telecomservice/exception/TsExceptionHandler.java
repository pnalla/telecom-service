package com.blng.telecomservice.exception;

import com.blng.telecomservice.api.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class TsExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(TsExceptionHandler.class);

  @ExceptionHandler({DataNotFoundException.class})
  public ResponseEntity<ApiError> handleNoDataFoundException(DataNotFoundException dataNotFoundException, HttpServletRequest httpServletRequest) {
    logger.error("exception occurred = ", dataNotFoundException);
    return new ResponseEntity<>(dataNotFoundException.getApiError(), HttpStatus.BAD_REQUEST);
  }
}
