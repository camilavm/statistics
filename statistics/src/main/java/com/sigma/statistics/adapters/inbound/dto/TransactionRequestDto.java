package com.sigma.statistics.adapters.inbound.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TransactionRequestDto {

  private BigDecimal amount;
  private Timestamp timestamp;
}
