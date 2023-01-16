package com.sigma.statistics.adapters.inbound.config;

import com.sigma.statistics.core.usecase.StatisticsUseCase;
import com.sigma.statistics.core.usecase.ports.input.StatisticsUseCasePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  StatisticsUseCasePort statisticsUseCasePort() {
    return new StatisticsUseCase();
  }

}
