package com.sigma.statistics.adapters.inbound.mapper;

import com.sigma.statistics.adapters.inbound.dto.TransactionRequestDto;
import com.sigma.statistics.core.domain.Transaction;
import java.math.RoundingMode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionMapper {

  public Transaction dtoToDomain(TransactionRequestDto transactionRequestDto) {
    return Transaction.builder()
        .amount(transactionRequestDto.getAmount().setScale(2, RoundingMode.HALF_UP))
        .timestamp(transactionRequestDto.getTimestamp())
        .build();
  }
}
