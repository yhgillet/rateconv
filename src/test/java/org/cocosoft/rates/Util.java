package org.cocosoft.rates;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
    public static BigDecimal round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

}
