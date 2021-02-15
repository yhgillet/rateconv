package org.cocosoft.rateconv.funqy;

import org.cocosoft.rateconv.rate.model.Rate;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertResult {
    private List<Rate> rates;
    private List<ValidationError> validationErrors;

    public ConvertResult(List<Rate> rates) {
        this.rates = rates;

    }

    public ConvertResult(ConstraintViolationException e) {
        this.validationErrors = e.getConstraintViolations().stream()
                .map(v -> new ValidationError(v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.toList());
    }

    public List<Rate> getRates() {
        return rates;
    }


    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }
}
