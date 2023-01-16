package com.sigma.statistics.adapters.inbound.mapper;

import com.sigma.statistics.adapters.inbound.dto.StatisticsResponseDto;
import com.sigma.statistics.core.domain.Statistics;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StatisticsMapper {

  public StatisticsResponseDto domainToResponse(Statistics statistics) {
    var statisticsResponseDto = StatisticsResponseDto.builder()
        .sum(statistics.getSum())
        .avg(statistics.getAvg())
        .max(statistics.getMax())
        .min(statistics.getMin())
        .count(statistics.getCount())
        .build();

    return statisticsResponseDto;
  }
}
