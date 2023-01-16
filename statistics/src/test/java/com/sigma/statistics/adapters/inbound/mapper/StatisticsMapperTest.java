package com.sigma.statistics.adapters.inbound.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.sigma.statistics.core.domain.Statistics;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class StatisticsMapperTest {

  private final BigDecimal sum = BigDecimal.valueOf(105.33);
  private final BigDecimal avg = BigDecimal.valueOf(35.11);
  private final BigDecimal max = BigDecimal.valueOf(89.21);
  private final BigDecimal min = BigDecimal.valueOf(3.75);
  private final Long count = 3L;


  @Test
  void shouldTransformDomainToResponse() {
    final var statisticsResponseDto = StatisticsMapper.domainToResponse(getStatistics());

    assertThat(statisticsResponseDto).isNotNull();
    assertThat(statisticsResponseDto.getSum()).isEqualTo(sum);
    assertThat(statisticsResponseDto.getAvg()).isEqualTo(avg);
    assertThat(statisticsResponseDto.getMax()).isEqualTo(max);
    assertThat(statisticsResponseDto.getMin()).isEqualTo(min);
    assertThat(statisticsResponseDto.getCount()).isEqualTo(count);
  }

  private Statistics getStatistics() {
    return Statistics.builder()
        .sum(sum)
        .avg(avg)
        .max(max)
        .min(min)
        .count(count)
        .build();
  }
}