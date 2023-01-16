package com.sigma.statistics.adapters.inbound.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class StatisticsResponseDto {
  private BigDecimal sum;
  private BigDecimal avg;
  private BigDecimal max;
  private BigDecimal min;
  private Long count;
}
