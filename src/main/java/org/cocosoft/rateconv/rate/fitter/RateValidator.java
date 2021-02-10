package org.cocosoft.rateconv.rate.fitter;

import org.apache.commons.math3.fitting.leastsquares.ParameterValidator;
import org.apache.commons.math3.linear.RealVector;
import org.cocosoft.rateconv.rate.BFRate;
import org.cocosoft.rateconv.rate.KissRate;
import org.cocosoft.rateconv.rate.RaceFlightRate;
import org.cocosoft.rateconv.rate.Rate;

public class RateValidator implements ParameterValidator {
  double[] maxValues;

  public RateValidator(Rate sourceRate) {
    if (sourceRate instanceof KissRate) {
      maxValues = new double[] {10, 1, 1};
    } else if (sourceRate instanceof BFRate) {
      maxValues = new double[] {3, 1, 1};
    }
    if (sourceRate instanceof RaceFlightRate) {
      maxValues = new double[] {2000, 100, 1000};
    }
  }

  @Override
  public RealVector validate(RealVector params) {
    for (int i = 0; i < 3; i++) {
      if (params.getEntry(i) < 0) {
        params.setEntry(i, 0);
      } else if (params.getEntry(i) > maxValues[i]) {
        params.setEntry(i, maxValues[i]);
      }
    }
    return params;
  }
}
