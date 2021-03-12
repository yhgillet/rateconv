package org.cocosoft.rateconv.rate.service.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.GroupSequence;
import javax.validation.Payload;
import javax.validation.groups.Default;

@Target( {TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {RateParamsValidator.class})
public @interface RateParamConstraint {

  String message() default "Invalid value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  public interface ConvGroup {

  }

  @GroupSequence( {Default.class, ConvGroup.class})
  public interface OrderedChecks {
  }

}
