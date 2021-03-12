package org.cocosoft.rateconv.rate.fitter;

import org.apache.commons.math3.fitting.leastsquares.ParameterValidator;
import org.apache.commons.math3.linear.RealVector;
import org.cocosoft.rateconv.rate.model.Rate;

public class RateValidator implements ParameterValidator {
    double[] maxValues;

    public RateValidator(Rate.Type rate) {
        switch (rate) {
            case BF:
                maxValues = new double[]{3, 0.99, 1};
                break;
            case KISS:
                maxValues = new double[]{10, 0.99, 1};
                break;
            case RF:
                maxValues = new double[]{2000, 100, 1000};
                break;
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
