package org.cocosoft.rateconv.rate;

import org.cocosoft.rateconv.rate.fitter.RateFitter;
import org.cocosoft.rateconv.rate.model.BFRate;
import org.cocosoft.rateconv.rate.model.KissRate;
import org.cocosoft.rateconv.rate.model.RaceFlightRate;
import org.cocosoft.rateconv.rate.model.Rate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class RateFitterTest {

    private BFRate bfRate = new BFRate(2.1, 0.28, 0.74);
    private KissRate kissRate = new KissRate(1.33, 0.73, 0.48);

    private RateFitter fitter = new RateFitter();

    @Test
    public void testConvertBFToKiss() {

        KissRate rate = (KissRate) fitter.convert(bfRate, Rate.Type.KISS, 1, 0, 0);

        System.out.println(bfRate);
        System.out.println(rate);

        Assertions.assertEquals(1.33D, rate.getRcRate(), 0.01);
        Assertions.assertEquals(0.73D, rate.getRate(), 0.01);
        Assertions.assertEquals(0.49D, rate.getRcCurve(), 0.01);

    }

    @Test
    public void testConvertBFToRaceFlight() {

        RaceFlightRate result = (RaceFlightRate) fitter.convert(bfRate, Rate.Type.RF, 100, 50, 100);


        System.out.println(bfRate);
        System.out.println(result);

        Assertions.assertEquals(354, result.getRate(), 1);
        Assertions.assertEquals(67, result.getExpo(), 1);
        Assertions.assertEquals(170, result.getAcrop(), 1);
    }

    @Test
    public void testConvertKissToBF() {

        BFRate result = (BFRate) fitter.convert(kissRate, Rate.Type.BF, 2, 0, 0);

        System.out.println(kissRate);
        System.out.println(result);
        Assertions.assertEquals(2.1, result.getRcRate(), 0.01);
        Assertions.assertEquals(0.25, result.getSuperRate(), 0.01);
        Assertions.assertEquals(0.65, result.getExpo(), 0.01);

    }

}