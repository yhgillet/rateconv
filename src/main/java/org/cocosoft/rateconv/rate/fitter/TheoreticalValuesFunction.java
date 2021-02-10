package org.cocosoft.rateconv.rate.fitter;

import java.util.Collection;

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

public class TheoreticalValuesFunction {
  /**
   * Function to fit.
   */
  private final ParametricUnivariateFunction f;
  /**
   * Observations.
   */
  private final double[] points;

  /**
   * @param f            function to fit.
   * @param observations Observations.
   */
  public TheoreticalValuesFunction(final ParametricUnivariateFunction f,
                                   final Collection<WeightedObservedPoint> observations) {
    this.f = f;

    final int len = observations.size();
    this.points = new double[len];

    int i = 0;
    for (WeightedObservedPoint obs : observations) {
      this.points[i++] = obs.getX();
    }
  }

  /**
   * @return the model function values.
   */
  public MultivariateVectorFunction getModelFunction() {
    return new MultivariateVectorFunction() {
      /** {@inheritDoc} */
      public double[] value(double[] p) {
        final int len = points.length;

        double[] values = new double[len];
        for (int i = 0; i < len; i++) {
          values[i] = f.value(points[i], p);
        }
        return values;
      }
    };
  }

  /**
   * @return the model function Jacobian.
   */
  public MultivariateMatrixFunction getModelFunctionJacobian() {
    return new MultivariateMatrixFunction() {
      /** {@inheritDoc} */
      public double[][] value(double[] p) {
        final int len = points.length;
        double[][] jacobian = new double[len][];
        for (int i = 0; i < len; i++) {
          jacobian[i] = f.gradient(points[i], p);
        }
        return jacobian;
      }
    };
  }
}
