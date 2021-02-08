package org.cocosoft.rates;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BFRate implements Rate {

    private double rcRate;
    private double superRate;
    private double expo;

    public BFRate(double rcRate, double superRate, double expo) {
        init(rcRate, superRate, expo);
    }

    @Override
    public double[] params() {
        return new double[]{rcRate, superRate, expo};
    }

    @Override
    public void init(double rcRate, double superRate, double expo) {
        this.rcRate = rcRate;
        this.superRate = superRate;
        this.expo = expo;
    }


    public double getRcRate() {
        return rcRate;
    }

    public double getSuperRate() {
        return superRate;
    }

    public double getExpo() {
        return expo;
    }

    private double clamp(double n, double minn, double maxn) {
        return Math.max(Math.min(maxn, n), minn);
    }


    public double calc(double rcCommand) {

        var rcRate = this.rcRate;
        if (rcRate > 2.0) {
            rcRate = rcRate + (14.54 * (rcRate - 2.0));
        }
        var a = (Math.pow(rcCommand, 4) * expo + rcCommand * (1.0 - expo));
        return 200 * rcRate * a / (1 - superRate * a);
    }

    public double calc_(double rcCommand) {

        var rcRate = this.rcRate;
        if (rcRate > 2.0) {
            rcRate = rcRate + (14.54 * (rcRate - 2.0));
        }

        if (expo != 0) {
            rcCommand = rcCommand * Math.pow(rcCommand, 3) * expo + rcCommand * (1.0 - expo);
        }

        var angleRate = 200.0 * rcRate * rcCommand;
        if (superRate != 0) {
            var rcSuperFactor = 1.0 / (clamp(1.0 - rcCommand * (superRate), 0.01, 1.00));
            angleRate *= rcSuperFactor;
        }
        return angleRate;
    }

    @Override
    public String toString() {
        return "BF{" +
                "rcRate=" + BigDecimal.valueOf(rcRate).setScale(2, RoundingMode.HALF_UP) +
                ", superRate=" + BigDecimal.valueOf(superRate).setScale(2, RoundingMode.HALF_UP) +
                ", expo=" + BigDecimal.valueOf(expo).setScale(2, RoundingMode.HALF_UP) +
                ", max deg/s=" + BigDecimal.valueOf(calc(1.0)).setScale(3, RoundingMode.HALF_UP) +
                '}';
    }
}
