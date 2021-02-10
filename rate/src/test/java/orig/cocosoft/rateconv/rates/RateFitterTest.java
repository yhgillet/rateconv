package orig.cocosoft.rateconv.rates;

import org.cocosoft.rateconv.rates.BFRate;
import org.cocosoft.rateconv.rates.KissRate;
import org.cocosoft.rateconv.rates.RaceFlightRate;
import org.cocosoft.rateconv.rates.fitter.RateFitter;
import org.cocosoft.rateconv.rates.fitter.RateResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class RateFitterTest {

  private BFRate bfRate = new BFRate(2.1, 0.28, 0.74);
  private KissRate kissRate = new KissRate(1.33, 0.73, 0.48);

  private RateFitter convert = new RateFitter();

  @Test
  public void testConvertBFToKiss() {

    RateResult out = convert.convert(bfRate, new KissRate(1, 0, 0));
    KissRate rate = (KissRate) out.getRate();
    System.out.println(bfRate);
    System.out.println(out);

    Assertions.assertEquals(1.33D, rate.getRcRate(), 0.01);
    Assertions.assertEquals(0.73D, rate.getRate(), 0.01);
    Assertions.assertEquals(0.49D, rate.getRcCurve(), 0.01);

  }

  @Test
  public void testConvertBFToRaceFlight() {

    RateResult out = convert.convert(bfRate, new RaceFlightRate(100, 50, 100));
    RaceFlightRate result = (RaceFlightRate) out.getRate();

    System.out.println(bfRate);
    System.out.println(out);

    Assertions.assertEquals(354, result.getRate(), 1);
    Assertions.assertEquals(67, result.getExpo(), 1);
    Assertions.assertEquals(170, result.getAcrop(), 1);
  }

  @Test
  public void testConvertKissToBF() {

    RateResult out = convert.convert(kissRate, new BFRate(2, 0, 0));
    BFRate result = (BFRate) out.getRate();

    System.out.println(kissRate);
    System.out.println(out);
    Assertions.assertEquals(2.1, result.getRcRate(), 0.01);
    Assertions.assertEquals(0.25, result.getSuperRate(), 0.01);
    Assertions.assertEquals(0.65, result.getExpo(), 0.01);

  }

}