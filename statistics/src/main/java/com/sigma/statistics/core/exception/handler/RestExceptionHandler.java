package com.sigma.statistics.core.exception.handler;

import com.sigma.statistics.core.exception.InvalidJsonException;
import com.sigma.statistics.core.exception.NotParsableException;
import com.sigma.statistics.core.exception.TransactionOlder60SecondsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(NotParsableException.class)
  public ResponseEntity<?> handlerNotParsableException(NotParsableException ex) {
    return new ResponseEntity<>("", HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(InvalidJsonException.class)
  public ResponseEntity<?> handlerInvalidJsonException(InvalidJsonException ex) {
    return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TransactionOlder60SecondsException.class)
  public ResponseEntity<?> handlerTransactionOlder60SecondsException(TransactionOlder60SecondsException ex) {
    return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
  }
}
