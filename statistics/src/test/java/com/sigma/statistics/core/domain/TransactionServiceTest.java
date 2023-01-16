package com.sigma.statistics.core.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class TransactionServiceTest {

  @InjectMocks
  private TransactionService transactionService;

  @Test
  void testTotalAmountLast60Seconds() {
    addAcceptableTransactionsIntoTransactionList();
    addIgnoredTransactionsIntoTransactionList(BigDecimal.valueOf(5.26));

    assertThat(transactionService.getTotalAmountLast60Seconds()).isEqualTo(BigDecimal.valueOf(120.42));
  }

  @Test
  void testAverageAmountLast60Seconds() {
    addAcceptableTransactionsIntoTransactionList();
    addIgnoredTransactionsIntoTransactionList(BigDecimal.valueOf(5.26));

    assertThat(transactionService.getAverageAmountLast60Seconds()).isEqualTo(BigDecimal.valueOf(40.14));
  }

  @Test
  void testMaxAmountLast60Seconds() {
    addAcceptableTransactionsIntoTransactionList();
    addIgnoredTransactionsIntoTransactionList(BigDecimal.valueOf(105.78));

    assertThat(transactionService.getMaxAmountLast60Seconds()).isEqualTo(BigDecimal.valueOf(100.44));
  }

  @Test
  void testMinAmountLast60Seconds() {
    addAcceptableTransactionsIntoTransactionList();
    addIgnoredTransactionsIntoTransactionList(BigDecimal.valueOf(5.26));

    assertThat(transactionService.getMinAmountLast60Seconds()).isEqualTo(BigDecimal.valueOf(7.12));
  }

  private void addAcceptableTransactionsIntoTransactionList() {
    var acceptableTimestamp = new Timestamp(System.currentTimeMillis());
    acceptableTimestamp.setSeconds(acceptableTimestamp.getSeconds() - 1);
    transactionService.getTransactionList().add(new Transaction(BigDecimal.valueOf(12.86), acceptableTimestamp));
    transactionService.getTransactionList().add(new Transaction(BigDecimal.valueOf(7.12), acceptableTimestamp));
    transactionService.getTransactionList().add(new Transaction(BigDecimal.valueOf(100.44), acceptableTimestamp));
  }

  private void addIgnoredTransactionsIntoTransactionList(final BigDecimal amount) {
    var timestampOut = new Timestamp(System.currentTimeMillis());
    timestampOut.setSeconds(timestampOut.getSeconds() - 75);
    transactionService.getTransactionList().add(new Transaction(amount, timestampOut));
  }
}