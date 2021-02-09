package org.cocosoft.rates;

import org.cocosoft.rates.fitter.RateFitter;
import org.cocosoft.rates.fitter.RateResult;
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

        RateResult<KissRate> out = convert.convert(bfRate, new KissRate(1, 0, 0));
        KissRate result = out.getRate();
        System.out.println(bfRate);
        System.out.println(out);

        Assertions.assertEquals(1.33D, result.getRcRate(),0.01);
        Assertions.assertEquals(0.73D, result.getRate(), 0.01);
        Assertions.assertEquals(0.49D, result.getRcCurve(), 0.01);

    }

    @Test
    public void testConvertBFToRaceFlight() {

        RateResult<RaceFlightRate>  out = convert.convert(bfRate, new RaceFlightRate(100, 50, 100));
        RaceFlightRate result = out.getRate();

        System.out.println(bfRate);
        System.out.println(out);

        Assertions.assertEquals(354, result.getRate(),1);
        Assertions.assertEquals(67, result.getExpo(),1);
        Assertions.assertEquals(170, result.getAcrop(),1);
    }

    @Test
    public void testConvertKissToBF() {

        RateResult<BFRate> out = convert.convert(kissRate, new BFRate(2, 0, 0));
        BFRate result = out.getRate();

        System.out.println(kissRate);
        System.out.println(out);
        Assertions.assertEquals(2.1, result.getRcRate(),0.01);
        Assertions.assertEquals(0.25, result.getSuperRate(),0.01);
        Assertions.assertEquals(0.65, result.getExpo(),0.01);

    }

}