package org.cocosoft.rates;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RaceFlightRate implements Rate {
    private Type type = Type.RF;
    private double rate;
    private double expo;
    private double acrop;

    public RaceFlightRate(double rate, double expo, double acrop) {
        init(rate, expo, acrop);
    }

    @Override
    public Type getType() {
        return type;
    }

    public double getRate() {
        return rate;
    }

    public double getExpo() {
        return expo;
    }

    public double getAcrop() {
        return acrop;
    }



    @Override
    public double[] getParams() {
        return new double[]{rate, expo, acrop};
    }

    @Override
    public void init(double rate, double expo, double acrop) {
        this.rate = rate;
        this.expo = expo;
        this.acrop = acrop;
    }

    public double calc(double rcCommand) {
        var returnValue = (1 + 0.01 * expo * (rcCommand * rcCommand - 1.0)) * rcCommand;
        return returnValue * (rate + (Math.abs(returnValue) * rate * acrop * 0.01));
    }

    @Override
    public String toString() {
        return "{type: 'rf', " +
                "rate: " + BigDecimal.valueOf(rate).setScale(1, RoundingMode.HALF_UP) +
                ", expo: " + BigDecimal.valueOf(expo).setScale(1, RoundingMode.HALF_UP) +
                ", acrop: " + BigDecimal.valueOf(acrop).setScale(1, RoundingMode.HALF_UP) +
                ", max: " + BigDecimal.valueOf(calc(1.0)).setScale(3, RoundingMode.HALF_UP) +
                '}';
    }
}
