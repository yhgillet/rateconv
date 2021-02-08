package org.cocosoft.rates.fitter;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.cocosoft.rates.BFRate;

public class BFTransformFunction implements ParametricUnivariateFunction {
    private BFRate rate;

    public BFTransformFunction(BFRate rate) {
        this.rate = rate;
    }

    @Override
    public double value(double x, double... parameters) {
        rate.init(parameters[0], parameters[1], parameters[2]);
        return rate.calc(x);
    }

    @Override
    public double[] gradient(double x, double... parameters) {
        double rcRate = parameters[0];
        double superRate = parameters[1];
        double expo = parameters[2];

        if (rcRate > 2.0) {
            rcRate = rcRate + (14.54 * (rcRate - 2.0));
        }

        double dA = 200 * (expo * Math.pow(x, 4) + x * (1 - expo)) / (1 - expo * Math.pow(x, 4) - superRate * x + x * expo * superRate);
        double dB = 200 * rcRate * x * (expo * Math.pow(x, 4) + x * Math.pow(1 - expo, 2)) / Math.pow(1 - expo * Math.pow(x, 4) - superRate * x + x * expo * superRate, 2);
        double dC = 200 * rcRate * (Math.pow(x, 5) * (1 - superRate) + Math.pow(x, 4) - x) / Math.pow(1 - expo * Math.pow(x, 4) - superRate * x + x * expo * superRate, 2);

        return new double[]{dA, dB, dC};
    }
}
