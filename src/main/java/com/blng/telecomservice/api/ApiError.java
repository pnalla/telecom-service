package com.blng.telecomservice.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
  private String errorId;
  private String message;
}
