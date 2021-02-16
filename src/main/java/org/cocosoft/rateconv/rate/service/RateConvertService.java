package org.cocosoft.rateconv.rate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.cocosoft.rateconv.rate.fitter.RateFitter;
import org.cocosoft.rateconv.rate.model.BFRate;
import org.cocosoft.rateconv.rate.model.KissRate;
import org.cocosoft.rateconv.rate.model.RaceFlightRate;
import org.cocosoft.rateconv.rate.model.Rate;
import org.cocosoft.rateconv.rate.service.validation.RateParamConstraint;
import org.cocosoft.rateconv.rate.service.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RateConvertService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private double[] kissGuess = {1, 0, 0};
  private double[] bfGuess = {1, 0, 0};
  private double[] rfGuess = {100, 0, 0};

  @Inject
  private Validator validator;

  public ConvertResult convert(ConvertParams params) {

    Set<ConstraintViolation<ConvertParams>> violations = validator.validate(params, RateParamConstraint.OrderedChecks.class);

    if (!violations.isEmpty()) {
      return new ConvertResult(null, violations.stream()
        .map(v -> new ValidationError(v.getPropertyPath().toString(), v.getMessage()))
        .collect(Collectors.toList()));
    }

    Rate.Type from = params.getFrom();
    List<Rate> results = new ArrayList<>(2);
    RateFitter fitter = new RateFitter();
    switch (from) {
      case BF: {
        BFRate rate = new BFRate(params.getRateParam1(), params.getRateParam2(), params.getRateParam3());
        results.add(fitter.convert(rate, Rate.Type.KISS, kissGuess));
        results.add(fitter.convert(rate, Rate.Type.RF, rfGuess));
        break;
      }
      case KISS: {
        KissRate rate = new KissRate(params.getRateParam1(), params.getRateParam2(), params.getRateParam3());
        results.add(fitter.convert(rate, Rate.Type.BF, bfGuess));
        results.add(fitter.convert(rate, Rate.Type.RF, rfGuess));
        break;
      }
      case RF: {
        RaceFlightRate rate = new RaceFlightRate(params.getRateParam1(), params.getRateParam2(), params.getRateParam3());
        results.add(fitter.convert(rate, Rate.Type.KISS, kissGuess));
        results.add(fitter.convert(rate, Rate.Type.BF, bfGuess));
        break;
      }
      default:
        throw new IllegalStateException("Unknown type");
    }

    return new ConvertResult(results, null);
  }


}
