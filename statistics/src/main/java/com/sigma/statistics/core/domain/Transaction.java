package com.sigma.statistics.core.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Transaction {
  private BigDecimal amount;
  private Timestamp timestamp;
}