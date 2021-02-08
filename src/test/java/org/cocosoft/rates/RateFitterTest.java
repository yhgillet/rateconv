package org.cocosoft.rates;

import org.cocosoft.rates.fitter.RateFitter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.cocosoft.rates.Util.round;

class RateFitterTest {

    private BFRate bfRate = new BFRate(2.1, 0.28, 0.74);
    private KissRate kissRate = new KissRate(1.33, 0.73, 0.48);

    private RateFitter convert = new RateFitter();

    @Test
    public void testConvertBFToKiss() {

        KissRate result = convert.convert(bfRate, new KissRate(1, 0, 0));

        System.out.println(bfRate);
        System.out.println(result);

        Assertions.assertEquals(BigDecimal.valueOf(1.33), round(result.getRcRate()));
        Assertions.assertEquals(BigDecimal.valueOf(0.73), round(result.getRate()));
        Assertions.assertEquals(BigDecimal.valueOf(0.48), round(result.getRcCurve()));
    }

    @Test
    public void testConvertBFToRaceFlight() {

        RaceFlightRate result = convert.convert(bfRate, new RaceFlightRate(100, 50, 100));

        System.out.println(bfRate);
        System.out.println(result);

        Assertions.assertEquals(BigDecimal.valueOf(356.57), round(result.getRate()));
        Assertions.assertEquals(BigDecimal.valueOf(0.75), round(result.getExpo()));
        Assertions.assertEquals(BigDecimal.valueOf(0.45), round(result.getAcrop()));
    }

    @Test
    public void testConvertKissToBF() {

        BFRate result = convert.convert(kissRate, new BFRate(2, 0, 0));

        System.out.println(kissRate);
        System.out.println(result);

        Assertions.assertEquals(new BigDecimal("2.10"), round(result.getRcRate()));
        Assertions.assertEquals(new BigDecimal("0.25"), round(result.getSuperRate()));
        Assertions.assertEquals(new BigDecimal("0.65"), round(result.getExpo()));
    }

}