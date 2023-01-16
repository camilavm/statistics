package com.sigma.statistics.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NotParsableException extends RuntimeException {

  public NotParsableException() {
    super();
  }
}
