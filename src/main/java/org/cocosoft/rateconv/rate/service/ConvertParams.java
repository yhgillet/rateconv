package org.cocosoft.rateconv.rate.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.cocosoft.rateconv.rate.model.Rate;
import org.cocosoft.rateconv.rate.service.validation.RateParamConstraint;


@RateParamConstraint(groups = {RateParamConstraint.ConvGroup.class})
public class ConvertParams {
  @NotNull
  private Rate.Type from;

  @Min(0)
  @NotNull
  private Double rateParam1;

  @Min(0)
  @NotNull
  private Double rateParam2;

  @Min(0)
  @NotNull
  private Double rateParam3;

  public ConvertParams() {
  }

  public ConvertParams(Rate.Type from, double rateParam1, double rateParam2, double rateParam3) {
    this.from = from;
    this.rateParam1 = rateParam1;
    this.rateParam2 = rateParam2;
    this.rateParam3 = rateParam3;
  }

  public Rate.Type getFrom() {
    return from;
  }

  public void setFrom(Rate.Type from) {
    this.from = from;
  }

  public double getRateParam1() {
    return rateParam1;
  }

  public void setRateParam1(double rateParam1) {
    this.rateParam1 = rateParam1;
  }

  public double getRateParam2() {
    return rateParam2;
  }

  public void setRateParam2(double rateParam2) {
    this.rateParam2 = rateParam2;
  }

  public double getRateParam3() {
    return rateParam3;
  }

  public void setRateParam3(double rateParam3) {
    this.rateParam3 = rateParam3;
  }

  @Override
  public String toString() {
    return "ConvertParams{" +
      "from=" + from +
      ", rateParam1=" + rateParam1 +
      ", rateParam2=" + rateParam2 +
      ", rateParam3=" + rateParam3 +
      '}';
  }

}
