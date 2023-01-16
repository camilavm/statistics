package com.sigma.statistics.core.usecase;

import com.sigma.statistics.adapters.inbound.dto.StatisticsResponseDto;
import com.sigma.statistics.adapters.inbound.mapper.StatisticsMapper;
import com.sigma.statistics.core.domain.Statistics;
import com.sigma.statistics.core.domain.Transaction;
import com.sigma.statistics.core.domain.TransactionService;
import com.sigma.statistics.core.exception.NotParsableException;
import com.sigma.statistics.core.exception.TransactionOlder60SecondsException;
import com.sigma.statistics.core.usecase.ports.input.StatisticsUseCasePort;
import java.sql.Timestamp;

public class StatisticsUseCase implements StatisticsUseCasePort {

  private TransactionService transactionService = new TransactionService();

  private static final int RANGE_SECONDS = 60;

  @Override
  public void createNewTransaction(final Transaction transaction) {
    synchronized (this) {
      if (transaction.getTimestamp().after(new Timestamp(System.currentTimeMillis()))) {
        throw new NotParsableException();
      }

      var time60secondsAgo = new Timestamp(System.currentTimeMillis());
      time60secondsAgo.setSeconds(time60secondsAgo.getSeconds() - RANGE_SECONDS);
      if (transaction.getTimestamp().before(time60secondsAgo)) {
        throw new TransactionOlder60SecondsException();
      }

      transactionService.getTransactionList().add(transaction);
    }
  }

  @Override
  public StatisticsResponseDto getStatistics() {
    synchronized (this) {
      var statistics = Statistics.builder()
          .sum(transactionService.getTotalAmountLast60Seconds())
          .avg(transactionService.getAverageAmountLast60Seconds())
          .max(transactionService.getMaxAmountLast60Seconds())
          .min(transactionService.getMinAmountLast60Seconds())
          .count(transactionService.getTotalNumberOfTransactionsLast60Seconds())
          .build();

      return StatisticsMapper.domainToResponse(statistics);
    }
  }

  @Override
  public void deleteTransactions() {
    synchronized (this) {
      this.transactionService = new TransactionService();
    }
  }
}
