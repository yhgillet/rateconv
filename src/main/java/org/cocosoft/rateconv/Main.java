package org.cocosoft.rateconv;

import org.cocosoft.rateconv.rate.model.Rate;
import org.cocosoft.rateconv.rate.service.ConvertParams;
import org.cocosoft.rateconv.rate.service.RateConvertService;

import java.util.List;

public class Main {

    private static void printUsage() {
        System.out.println("Usage: <type> <param1> <param2> <param3>");
        System.out.println("Where: <type> in [bf,ki,rf] and <paramX> are the rate parameters");
        System.out.println("bf: <rc_rate> <super_rate> <rc_expo>");
        System.out.println("ki: <rc_rate> <rate> <rc_curve>");
        System.out.println("rf: <rate> <expo> <acro_plus>");

        System.exit(1);
    }


    public static void main(String[] args) {
        if (args.length != 4) {
            printUsage();
        }
        String type = args[0];

        double param1 = Double.parseDouble(args[1]);
        double param2 = Double.parseDouble(args[2]);
        double param3 = Double.parseDouble(args[3]);

        RateConvertService service = new RateConvertService();

        switch (type) {
            case "bf": {
                output(service.convert(new ConvertParams(Rate.Type.BF, param1, param2, param3)));
                break;
            }

            case "ki": {
                output(service.convert(new ConvertParams(Rate.Type.KISS, param1, param2, param3)));
                break;
            }
            case "rf": {
                output(service.convert(new ConvertParams(Rate.Type.RF, param1, param2, param3)));
                break;
            }
            default:
                printUsage();
        }
    }

    private static void output(List<Rate> result) {
        result.stream().map(Rate::toString).forEach(System.out::println);
    }
}
