package orig.cocosoft.rateconv.rates;

import static orig.cocosoft.rateconv.rates.Util.round;

import java.math.BigDecimal;

import org.cocosoft.rateconv.rates.KissRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KissRateTest {


  @Test
  public void testCalc() {
    KissRate rate = new KissRate(0.93, 0.68, 0.12);
    Assertions.assertEquals(new BigDecimal("38.10"), round(rate.calc(0.2)));
    Assertions.assertEquals(new BigDecimal("581.25"), round(rate.calc(1)));
  }
}