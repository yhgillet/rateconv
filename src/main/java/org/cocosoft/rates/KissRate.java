package org.cocosoft.rates;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class KissRate implements Rate {
    private double rcRate;
    private double rate;
    private double rcCurve;

    public KissRate(double rcRate, double rate, double rcCurve) {
        init(rcRate, rate, rcCurve);
    }

    @Override
    public double[] params() {
        return new double[]{rcRate, rate, rcCurve};
    }

    @Override
    public void init(double rcRate, double rate, double rcCurve) {
        this.rcRate = rcRate;
        this.rate = rate;
        this.rcCurve = rcCurve;
    }

    public double getRcRate() {
        return rcRate;
    }

    public double getRate() {
        return rate;
    }

    public double getRcCurve() {
        return rcCurve;
    }


    public double calc(double rcCommand) {
        double a = (Math.pow(rcCommand, 3) * rcCurve + (1 - rcCurve) * rcCommand);
        return (200 * rcRate * a) / (1 - rate * rcCommand);
    }

    public double calc_old(double rcCommand) {
        double kissRpyUseRates = 1 - rcCommand * rate;
        double kissRxRaw = rcCommand * 1000;
        double kissTempCurve = (kissRxRaw * kissRxRaw / 1000000);
        rcCommand = ((rcCommand * kissTempCurve) * rcCurve + rcCommand * (1 - rcCurve)) * (rcRate / 10);
        double kissAngle = ((2000.0 * (1.0 / kissRpyUseRates)) * rcCommand); // setpoint is calculated directly here
        return kissAngle;
    }

    @Override
    public String toString() {
        return "{type: 'ki'," +
                "rcRate:" + BigDecimal.valueOf(rcRate).setScale(2, RoundingMode.HALF_UP) +
                ", rate:" + BigDecimal.valueOf(rate).setScale(2, RoundingMode.HALF_UP) +
                ", rcCurve:" + BigDecimal.valueOf(rcCurve).setScale(2, RoundingMode.HALF_UP) +
                ", max:" + BigDecimal.valueOf(calc(1.0)).setScale(2, RoundingMode.HALF_UP) +
                '}';
    }
}
