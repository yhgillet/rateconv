package org.cocosoft.rates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.cocosoft.rates.Util.round;

class BFRateTest {

    @Test
    public void testCalc() {
        var rate = new BFRate(2.1, 0.28, 0.74);

        Assertions.assertEquals(new BigDecimal("38.37"), round(rate.calc_(0.2)));
        Assertions.assertEquals(new BigDecimal("131.78"), round(rate.calc_(0.5)));
        Assertions.assertEquals(new BigDecimal("987.22"), round(rate.calc_(1)));

        Assertions.assertEquals(new BigDecimal("38.37"), round(rate.calc(0.2)));
        Assertions.assertEquals(new BigDecimal("131.78"), round(rate.calc(0.5)));
        Assertions.assertEquals(new BigDecimal("987.22"), round(rate.calc(1)));
    }


}