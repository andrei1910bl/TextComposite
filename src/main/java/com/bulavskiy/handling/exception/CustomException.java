package com.bulavskiy.handling.exception;

public class CustomException extends Exception{
  public CustomException() {
    super();
  }

  public CustomException(String message) {
    super(message);
  }

  public CustomException(Throwable reason) {
    super(reason);
  }

  public CustomException(String message, Throwable reason) {
    super(message, reason);
  }
}
