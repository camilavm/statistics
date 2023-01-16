package com.sigma.statistics.adapters.inbound.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.sigma.statistics.adapters.inbound.dto.TransactionRequestDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;

class TransactionMapperTest {
  private final Timestamp timestamp = new Timestamp(2023, 1, 15, 9, 35, 21, 05);

  @Test
  void shouldTransformRequestDtoToDomain() {
    final var transactionDomain = TransactionMapper.dtoToDomain(getTransactionDto(BigDecimal.valueOf(10.12)));

    assertThat(transactionDomain).isNotNull();
    assertThat(transactionDomain.getAmount()).isEqualTo(BigDecimal.valueOf(10.12));
    assertThat(transactionDomain.getTimestamp()).isEqualTo(timestamp);
  }

  @Test
  void shouldTransformRequestDtoToDomainRoundUp() {
    final var transactionDomain = TransactionMapper.dtoToDomain(getTransactionDto(BigDecimal.valueOf(10.345)));

    assertThat(transactionDomain).isNotNull();
    assertThat(transactionDomain.getAmount()).isEqualTo(BigDecimal.valueOf(10.35));
    assertThat(transactionDomain.getTimestamp()).isEqualTo(timestamp);
  }

  @Test
  void shouldTransformRequestDtoToDomainFormatTwoDecimal() {
    final var transactionDomain = TransactionMapper.dtoToDomain(getTransactionDto(BigDecimal.valueOf(10.3)));

    assertThat(transactionDomain).isNotNull();
    assertThat(transactionDomain.getAmount()).isEqualTo(BigDecimal.valueOf(10.30).setScale(2, RoundingMode.HALF_UP));
    assertThat(transactionDomain.getTimestamp()).isEqualTo(timestamp);
  }

  private TransactionRequestDto getTransactionDto(BigDecimal amount) {
    return TransactionRequestDto.builder()
        .amount(amount)
        .timestamp(timestamp)
        .build();
  }

}