package org.cocosoft.rateconv.rate.service;

import java.util.List;

import org.cocosoft.rateconv.rate.model.Rate;
import org.cocosoft.rateconv.rate.service.validation.ValidationError;

public class ConvertResult {
  private List<Rate> result;
  private List<ValidationError> validationErrors;

  public ConvertResult(List<Rate> result, List<ValidationError> validationErrors) {
    this.result = result;
    this.validationErrors = validationErrors;
  }

  public List<Rate> getResult() {
    return result;
  }


  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }
}
