package org.cocosoft.rateconv.rate.fitter;


import org.cocosoft.rateconv.rate.Rate;

public class RateResult<T extends Rate> {
  private T rate;
  private double score;

  public RateResult(T rate, double score) {
    this.rate = rate;
    this.score = score;
  }

  public T getRate() {
    return rate;
  }

  public double getScore() {
    return score;
  }

  @Override
  public String toString() {
    return "{result: " + rate + ", fit:" + score + "}";
  }
}
