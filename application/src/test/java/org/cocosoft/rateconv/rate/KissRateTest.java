package org.cocosoft.rateconv.rate;


import org.cocosoft.rateconv.rate.model.KissRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.cocosoft.rateconv.rate.Util.round;

class KissRateTest {


    @Test
    public void testCalc() {
        KissRate rate = new KissRate(0.93, 0.68, 0.12);
        Assertions.assertEquals(new BigDecimal("38.10"), round(rate.calc(0.2)));
        Assertions.assertEquals(new BigDecimal("581.25"), round(rate.calc(1)));
    }
}