package com.sigma.statistics.core.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TransactionService {
  private List<Transaction> transactionList = new ArrayList<>();

  private static final int RANGE_SECONDS = 60;

  public BigDecimal getTotalAmountLast60Seconds() {
    Timestamp initialTime = getInitialTime();

    return transactionList.stream()
        .filter(item -> item.getTimestamp().after(initialTime))
        .map(Transaction::getAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
  }

  public BigDecimal getAverageAmountLast60Seconds() {
    var totalAmount = getTotalAmountLast60Seconds();
    var numberTransactions = getTotalNumberOfTransactionsLast60Seconds();

    return numberTransactions == 0 ? BigDecimal.ZERO :
        (totalAmount.divide(BigDecimal.valueOf(numberTransactions), 2, RoundingMode.HALF_UP));
  }

  public BigDecimal getMaxAmountLast60Seconds() {
    Timestamp initialTime = getInitialTime();

    var transactionMaxAmount = transactionList.stream()
        .filter(item -> item.getTimestamp().after(initialTime))
        .max(Comparator.comparing(Transaction::getAmount))
        .orElse(new Transaction(BigDecimal.ZERO, initialTime));

    return transactionMaxAmount.getAmount();
  }

  public BigDecimal getMinAmountLast60Seconds() {
    Timestamp initialTime = getInitialTime();

    var transactionMaxAmount = transactionList.stream()
        .filter(item -> item.getTimestamp().after(initialTime))
        .min(Comparator.comparing(Transaction::getAmount))
        .orElse(new Transaction(BigDecimal.ZERO, initialTime));

    return transactionMaxAmount.getAmount();
  }

  public Long getTotalNumberOfTransactionsLast60Seconds() {
    Timestamp initialTime = getInitialTime();

    return transactionList.stream()
        .filter(item -> item.getTimestamp().after(initialTime))
        .count();
  }

  private static Timestamp getInitialTime() {
    var initialTime = new Timestamp(System.currentTimeMillis());
    initialTime.setSeconds(initialTime.getSeconds() - RANGE_SECONDS);
    return initialTime;
  }
}
