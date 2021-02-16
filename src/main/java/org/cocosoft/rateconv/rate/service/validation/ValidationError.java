package org.cocosoft.rateconv.rate.service.validation;

public class ValidationError {
  private String name;
  private String message;

  public ValidationError(String name, String message) {
    this.name = name;
    this.message = message;
  }

  public String getName() {
    return name;
  }

  public String getMessage() {
    return message;
  }
}
