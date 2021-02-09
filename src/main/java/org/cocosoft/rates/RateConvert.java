package org.cocosoft.rates;

import org.cocosoft.rates.fitter.RateFitter;
import org.cocosoft.rates.fitter.RateResult;

public class RateConvert {

    private static void printUsage() {
        System.out.println("Usage: <type> <param1> <param2> <param3>");
        System.out.println("Where: <type> in [bf,ki,rf] and <paramX> are the rate parameters");
        System.out.println("bf: <rc_rate> <super_rate> <rc_expo>");
        System.out.println("ki: <rc_rate> <rate> <rc_curve>");
        System.out.println("rf: <rate> <expo> <acro_plus>");

        System.exit(1);
    }

    private static void output(RateResult rateResult) {
        //System.out.println(rateResult.getRate());
        System.out.println(rateResult);
    }

    public static void main(String[] args) {
        if (args.length != 4) {
            printUsage();
        }
        String type = args[0];

        double param1 = Double.parseDouble(args[1]);
        double param2 = Double.parseDouble(args[2]);
        double param3 = Double.parseDouble(args[3]);

        RateFitter fitter = new RateFitter();

        KissRate destKiss = new KissRate(1, 0.2, 0.1);
        BFRate destBf = new BFRate(1, 0.2, 0.1);
        RaceFlightRate destRF = new RaceFlightRate(100, 50, 100);

        switch (type) {
            case "bf" -> {
                output(fitter.convert(new BFRate(param1, param2, param3), destKiss));
                output(fitter.convert(new BFRate(param1, param2, param3), destRF));
            }
            case "ki" -> {
                output(fitter.convert(new KissRate(param1, param2, param3), destBf));
                output(fitter.convert(new KissRate(param1, param2, param3), destRF));
            }
            case "rf" -> {
                output(fitter.convert(new RaceFlightRate(param1, param2, param3), destKiss));
                output(fitter.convert(new RaceFlightRate(param1, param2, param3), destBf));
            }
            default -> printUsage();
        }
    }
}
