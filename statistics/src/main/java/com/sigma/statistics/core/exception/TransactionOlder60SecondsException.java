package com.sigma.statistics.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class TransactionOlder60SecondsException extends RuntimeException {

  public TransactionOlder60SecondsException() {
    super();
  }
}
