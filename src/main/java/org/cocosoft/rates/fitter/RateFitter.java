package org.cocosoft.rates.fitter;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.fitting.leastsquares.*;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.cocosoft.rates.BFRate;
import org.cocosoft.rates.KissRate;
import org.cocosoft.rates.RaceFlightRate;
import org.cocosoft.rates.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class RateFitter {

    private List<WeightedObservedPoint> getSampleData(Rate source) {
        WeightedObservedPoints points = new WeightedObservedPoints();

        for (int i = 0; i < 100; i++) {
            double command = 0.01 * (i + 1);
            double output = source.calc(command);
            //double weight = 1;
            //double weight = 2 - Math.pow(1 - 2 * command, 2);
            double weight = 2 - Math.pow(1 -  command/2, 2);

            points.add(weight, command, output);
        }
        return points.toList();

    }

    public <T extends Rate> T fit(ParametricUnivariateFunction function, Rate sourceRate, T targetRate) {
        List<WeightedObservedPoint> data = getSampleData(sourceRate);

        final double[] target = new double[data.size()];
        final double[] weights = new double[data.size()];
        double[] initialGuess = targetRate.params();

        for (int i = 0; i < target.length; i++) {
            target[i] = data.get(i).getY();
            weights[i] = data.get(i).getWeight();
        }

        TheoreticalValuesFunction model = new TheoreticalValuesFunction(function, data);

        ConvergenceChecker<LeastSquaresProblem.Evaluation> checker = new EvaluationRmsChecker(10e-4);

        LeastSquaresProblem problem = new LeastSquaresBuilder().
                maxIterations(1_000_000).
                maxEvaluations(10000).
                start(initialGuess).
                target(target).
                weight(new DiagonalMatrix(weights)).
                checker(checker).
                model(model.getModelFunction(), model.getModelFunctionJacobian()).
                build();

        LeastSquaresOptimizer optimizer = new LevenbergMarquardtOptimizer();

        LeastSquaresOptimizer.Optimum optimum = optimizer.optimize(problem);

        double[] result = optimum.getPoint().toArray();

        targetRate.init(result[0], result[1], result[2]);

        System.out.println("Fit score is " + eval(sourceRate, targetRate));
        return targetRate;
    }


    public <T extends Rate> T convert(Rate originRate, T targetRate) {
        if (originRate.getClass() == targetRate.getClass()) {
            throw new IllegalArgumentException("same origin and target ");
        }
        ParametricUnivariateFunction transformFunction = null;

        if (targetRate instanceof KissRate) {
            transformFunction = new KissTransformFunction((KissRate) targetRate);
        }
        if (targetRate instanceof BFRate) {
            transformFunction = new BFTransformFunction((BFRate) targetRate);
        }
        if (targetRate instanceof RaceFlightRate) {
            transformFunction = new RaceFlightTransformFunction((RaceFlightRate) targetRate);
        }

        return fit(transformFunction, originRate, targetRate);

    }


    private double eval(Rate origin, Rate target) {
        double diff = 0;
        int count = 0;
        for (double command = 0.01; command <= 1.0; command += 0.01) {
            double expected = origin.calc(command);
            double actual = target.calc(command);
            diff += Math.abs(expected - actual) / expected;
            count++;
        }
        return BigDecimal.valueOf(100 - 100 * diff / count).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

}
