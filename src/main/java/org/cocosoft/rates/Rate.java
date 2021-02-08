package org.cocosoft.rates;

public interface Rate {

    double[] params();

    void init(double param1, double param2, double param3);

    double calc(double command);
}
