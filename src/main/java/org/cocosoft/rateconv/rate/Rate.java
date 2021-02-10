package org.cocosoft.rateconv.rate;

public interface Rate {
  Type getType();
  
  double[] params();

  void init(double param1, double param2, double param3);

  double calc(double command);

  enum Type {
    BF, KISS, RF
  }
}
