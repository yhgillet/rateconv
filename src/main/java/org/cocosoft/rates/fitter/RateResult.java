package org.cocosoft.rates.fitter;


import org.cocosoft.rates.Rate;

public class RateResult<T extends Rate> {
    private T rate;
    private double fitScore;

    public RateResult(T rate, double fitScore) {
        this.rate = rate;
        this.fitScore = fitScore;
    }

    public T getRate() {
        return rate;
    }

    public double getFitScore() {
        return fitScore;
    }

    @Override
    public String toString() {
        return "{result: " + rate + ", fit:"  + fitScore +"}";
    }
}
