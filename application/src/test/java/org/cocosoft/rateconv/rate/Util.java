package org.cocosoft.rateconv.rate;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
  public static BigDecimal round(double value) {
    return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
  }

  public static boolean equals(double expected, double actual, double tolerance) {
    if ((Math.abs(expected - actual) / expected) > tolerance) {
      return false;
    }
    return true;
  }
}
