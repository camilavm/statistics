package com.sigma.statistics.adapters.inbound;

import com.sigma.statistics.adapters.inbound.dto.StatisticsResponseDto;
import com.sigma.statistics.adapters.inbound.dto.TransactionRequestDto;
import com.sigma.statistics.adapters.inbound.mapper.TransactionMapper;
import com.sigma.statistics.core.domain.Transaction;
import com.sigma.statistics.core.exception.NotParsableException;
import com.sigma.statistics.core.usecase.ports.input.StatisticsUseCasePort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

  private final StatisticsUseCasePort statisticsUseCasePort;

  public StatisticsController(final StatisticsUseCasePort statisticsUseCasePort) {
    this.statisticsUseCasePort = statisticsUseCasePort;
  }

  @PostMapping("/transactions")
  @ResponseStatus(HttpStatus.CREATED)
  public void createNewTransaction(@RequestBody final TransactionRequestDto transactionRequestDto) {
    Transaction transaction;

    try {
      transaction = TransactionMapper.dtoToDomain(transactionRequestDto);
    } catch (Exception e) {
      throw new NotParsableException();
    }

    this.statisticsUseCasePort.createNewTransaction(transaction);
  }

  @GetMapping("/statistics")
  public StatisticsResponseDto getStatistics() {
    return this.statisticsUseCasePort.getStatistics();
  }

  @DeleteMapping("/transactions")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTransactions() {
    this.statisticsUseCasePort.deleteTransactions();
  }
}
