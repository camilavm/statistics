package com.sigma.statistics.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import com.sigma.statistics.core.domain.Transaction;
import com.sigma.statistics.core.domain.TransactionService;
import com.sigma.statistics.core.exception.NotParsableException;
import com.sigma.statistics.core.exception.TransactionOlder60SecondsException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class StatisticsUseCaseTest {

  @InjectMocks
  private StatisticsUseCase statisticsUseCase;

  @Mock
  private TransactionService transactionService;

  @Test
  void createNewTransaction_ShouldThrownNotParsableException_WhenTransactionIsFuture() {
    var timeInFuture = new Timestamp(System.currentTimeMillis());
    timeInFuture.setHours(timeInFuture.getHours() + 1);

    var transaction = new Transaction(BigDecimal.TEN, timeInFuture);

    assertThatThrownBy(() -> statisticsUseCase.createNewTransaction(transaction))
        .isInstanceOf(NotParsableException.class);
  }

  @Test
  void createNewTransaction_ShouldThrownTransactionOlder60SecondsException_WhenTransactionIsOlderThan60Seconds() {
    var timeOlder60Seconds = new Timestamp(System.currentTimeMillis());
    timeOlder60Seconds.setMinutes(timeOlder60Seconds.getMinutes() - 2);

    var transaction = new Transaction(BigDecimal.TEN, timeOlder60Seconds);

    assertThatThrownBy(() -> statisticsUseCase.createNewTransaction(transaction))
        .isInstanceOf(TransactionOlder60SecondsException.class);
  }

  @Test
  void createNewTransaction_ShouldNotThrownException_WhenTimeIsWithinLast60Seconds() {
    var acceptableTime = new Timestamp(System.currentTimeMillis());
    acceptableTime.setSeconds(acceptableTime.getSeconds() - 20);

    var transaction = new Transaction(BigDecimal.TEN, acceptableTime);

    assertDoesNotThrow(() -> statisticsUseCase.createNewTransaction(transaction));
    verify(transactionService, only()).getTransactionList();
  }

  @Test
  void getStatistics_ShouldReturnStatisticsResponseDto() {
    var totalAmount = BigDecimal.valueOf(18.12);
    var averageAmount = BigDecimal.valueOf(6.04);
    var maxAmount = BigDecimal.valueOf(10.98);
    var minAmount = BigDecimal.valueOf(2.14);
    var numberTransaction = 3L;

    Mockito.when(transactionService.getTotalAmountLast60Seconds()).thenReturn(totalAmount);
    Mockito.when(transactionService.getAverageAmountLast60Seconds()).thenReturn(averageAmount);
    Mockito.when(transactionService.getMaxAmountLast60Seconds()).thenReturn(maxAmount);
    Mockito.when(transactionService.getMinAmountLast60Seconds()).thenReturn(minAmount);
    Mockito.when(transactionService.getTotalNumberOfTransactionsLast60Seconds()).thenReturn(numberTransaction);

    var statisticsResponseDto = statisticsUseCase.getStatistics();
    assertThat(statisticsResponseDto).isNotNull();
    assertThat(statisticsResponseDto.getSum()).isEqualTo(totalAmount);
    assertThat(statisticsResponseDto.getAvg()).isEqualTo(averageAmount);
    assertThat(statisticsResponseDto.getMax()).isEqualTo(maxAmount);
    assertThat(statisticsResponseDto.getMin()).isEqualTo(minAmount);
    assertThat(statisticsResponseDto.getCount()).isEqualTo(numberTransaction);
  }

  @Test
  void deleteTransactions_ShouldCleanTransactionList() {
    transactionService.getTransactionList().add(new Transaction(BigDecimal.TEN, new Timestamp(System.currentTimeMillis())));
    statisticsUseCase.deleteTransactions();

    assertThat(transactionService.getTransactionList()).isEmpty();
  }
}