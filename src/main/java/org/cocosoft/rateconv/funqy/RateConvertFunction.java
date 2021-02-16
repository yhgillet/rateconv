package org.cocosoft.rateconv.funqy;

import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.cocosoft.rateconv.rate.service.ConvertParams;
import org.cocosoft.rateconv.rate.service.ConvertResult;
import org.cocosoft.rateconv.rate.service.RateConvertService;
import org.cocosoft.rateconv.rate.service.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.funqy.Funq;

public class RateConvertFunction {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Inject
  private RateConvertService rateConvertService;

  @Funq
  public ConvertResult convert(ConvertParams params) {
    try {
      return rateConvertService.convert(params);
    } catch (ConstraintViolationException e) {
      return new ConvertResult(null, e.getConstraintViolations().stream()
        .map(v -> new ValidationError(v.getPropertyPath().toString(), v.getMessage()))
        .collect(Collectors.toList()));
    }
  }


}
