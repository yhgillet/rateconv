package org.cocosoft.rateconv.rates.fitter;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.cocosoft.rateconv.rates.RaceFlightRate;

public class RaceFlightTransformFunction implements ParametricUnivariateFunction {
  private RaceFlightRate rate;

  public RaceFlightTransformFunction(RaceFlightRate rate) {
    this.rate = rate;
  }

  @Override
  public double value(double x, double... parameters) {
    rate.init(parameters[0], parameters[1], parameters[2]);
    return rate.calc(x);
  }

  @Override
  public double[] gradient(double x, double... parameters) {
    double a = parameters[0];
    double b = parameters[1];
    double c = parameters[2];

    double dA = x * (1 + b * (x * x - 1) / 100) * (1 + x * c * (100 + b * (x * x - 1)) / 10000);
    double dB = a * x * (x * x - 1) * (x * c * (b * (x * x - 1) + 100) + 5000) / 500000;
    double dC = a * x * x * Math.pow(100 + b * (x * x - 1), 2) / 1000000;

    return new double[] {dA, dB, dC};
  }
}
