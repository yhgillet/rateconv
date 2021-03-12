package org.cocosoft.rateconv.rate.fitter;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.cocosoft.rateconv.rate.model.KissRate;

public class KissTransformFunction implements ParametricUnivariateFunction {
    private KissRate kissRate = new KissRate();

    @Override
    public double value(double x, double... parameters) {
        kissRate.init(parameters[0], parameters[1], parameters[2]);
        return kissRate.calc(x);
    }

    @Override
    public double[] gradient(double x, double... parameters) {
        double rcRate = parameters[0];
        double rate = parameters[1];
        double rcCurve = parameters[2];

        double dA = 200 * (rcCurve * Math.pow(x, 3) + x * (1 - rcCurve)) / (1 - rate * x);
        double dB = 200 * rcRate * x * (rcCurve * Math.pow(x, 3) + x * (1 - rcCurve)) / Math.pow(1 - rate * x, 2);
        double dC = 200 * rcRate * (Math.pow(x, 3) - x) / (1 - rate * x);
        return new double[]{dA, dB, dC};
    }
}
