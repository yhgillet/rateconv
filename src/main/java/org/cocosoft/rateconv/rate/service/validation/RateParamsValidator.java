package org.cocosoft.rateconv.rate.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.cocosoft.rateconv.rate.service.ConvertParams;

public class RateParamsValidator implements ConstraintValidator<RateParamConstraint, ConvertParams> {

  private RateParamConstraint param;

  double[] maxValuesBf = new double[] {3, 0.99, 1};
  double[] maxValuesKiss = new double[] {10, 0.99, 1};
  double[] maxValuesRf = new double[] {2000, 100, 1000};


  @Override
  public void initialize(RateParamConstraint constraintAnnotation) {
    this.param = constraintAnnotation;
  }

  private boolean checkMax(double value, double[] maxValues, int index, ConstraintValidatorContext context) {


    if (maxValues[index - 1] < value) {
      context.buildConstraintViolationWithTemplate("{org.cocosoft.validator.rateParam.toobig}")
        .addPropertyNode("rateParam" + index).addConstraintViolation();
      return false;
    }
    return true;
  }

  @Override
  public boolean isValid(ConvertParams value, ConstraintValidatorContext context) {

    boolean valid = true;
    context.disableDefaultConstraintViolation();

    if (value.getRateParam1() == 0) {

      context.buildConstraintViolationWithTemplate("{org.cocosoft.validator.rateParam.toosmall}")
        .addPropertyNode("rateParam1").addConstraintViolation();
      valid = false;
    }

    switch (value.getFrom()) {
      case KISS:
        valid &= checkMax(value.getRateParam1(), maxValuesKiss, 1, context);
        valid &= checkMax(value.getRateParam2(), maxValuesKiss, 2, context);
        valid &= checkMax(value.getRateParam3(), maxValuesKiss, 3, context);
        break;
      case BF:
        valid &= checkMax(value.getRateParam1(), maxValuesBf, 1, context);
        valid &= checkMax(value.getRateParam2(), maxValuesBf, 2, context);
        valid &= checkMax(value.getRateParam3(), maxValuesBf, 3, context);
        break;
      case RF:
        valid &= checkMax(value.getRateParam1(), maxValuesRf, 1, context);
        valid &= checkMax(value.getRateParam2(), maxValuesRf, 2, context);
        valid &= checkMax(value.getRateParam3(), maxValuesRf, 3, context);
        break;
    }
    return valid;
  }
}
