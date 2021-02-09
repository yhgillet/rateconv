package org.cocosoft.rates;

public interface Rate {
    enum Type {
        BF, KISS, RF
    }

    Type getType();

    double[] getParams();

    void init(double param1, double param2, double param3);

    double calc(double command);
}
