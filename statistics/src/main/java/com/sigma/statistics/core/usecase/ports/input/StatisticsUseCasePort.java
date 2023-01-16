package com.sigma.statistics.core.usecase.ports.input;


import com.sigma.statistics.adapters.inbound.dto.StatisticsResponseDto;
import com.sigma.statistics.core.domain.Transaction;

public interface StatisticsUseCasePort {

  void createNewTransaction(final Transaction transaction);

  StatisticsResponseDto getStatistics();

  void deleteTransactions();
}