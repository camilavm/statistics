package com.sigma.statistics.core.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Statistics {
  private BigDecimal sum;
  private BigDecimal avg;
  private BigDecimal max;
  private BigDecimal min;
  private Long count;
}
