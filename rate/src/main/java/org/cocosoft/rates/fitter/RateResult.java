package org.cocosoft.rates.fitter;


import org.cocosoft.rates.Rate;

public class RateResult {
    private Rate rate;
    private double fitScore;

    public RateResult(Rate rate, double fitScore) {
        this.rate = rate;
        this.fitScore = fitScore;
    }

    public Rate getRate() {
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
