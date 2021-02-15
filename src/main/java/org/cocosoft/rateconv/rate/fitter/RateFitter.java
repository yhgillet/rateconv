package org.cocosoft.rateconv.rate.fitter;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.fitting.leastsquares.*;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.cocosoft.rateconv.rate.model.BFRate;
import org.cocosoft.rateconv.rate.model.KissRate;
import org.cocosoft.rateconv.rate.model.RaceFlightRate;
import org.cocosoft.rateconv.rate.model.Rate;

import java.util.List;

public class RateFitter {


    private List<WeightedObservedPoint> getSampleData(Rate source) {
        WeightedObservedPoints points = new WeightedObservedPoints();

        for (int i = 0; i < 100; i++) {
            double command = 0.01 * (i + 1);
            double output = source.calc(command);
            double weight = 1;
            points.add(weight, command, output);
        }
        return points.toList();

    }

    public Rate from(Rate.Type type, double[] parameters) {
        switch (type) {
            case BF:
                return new BFRate(parameters[0], parameters[1], parameters[2]);
            case KISS:
                return new KissRate(parameters[0], parameters[1], parameters[2]);
            case RF:
                return new RaceFlightRate(parameters[0], parameters[1], parameters[2]);
        }
        return null;
    }

    public Rate fit(ParametricUnivariateFunction function, Rate sourceRate, Rate.Type targetRate, double... initialGuess) {
        List<WeightedObservedPoint> data = getSampleData(sourceRate);

        final double[] target = new double[data.size()];
        final double[] weights = new double[data.size()];

        for (int i = 0; i < target.length; i++) {
            target[i] = data.get(i).getY();
            weights[i] = data.get(i).getWeight();
        }

        TheoreticalValuesFunction model = new TheoreticalValuesFunction(function, data);

        ConvergenceChecker<LeastSquaresProblem.Evaluation> checker = new EvaluationRmsChecker(10e-4);

        ParameterValidator validator = new RateValidator(targetRate);

        LeastSquaresProblem problem = new LeastSquaresBuilder().
                maxIterations(1_000_000).
                maxEvaluations(10000).
                start(initialGuess).
                target(target).
                weight(new DiagonalMatrix(weights)).
                checker(checker).
                parameterValidator(validator).
                model(model.getModelFunction(), model.getModelFunctionJacobian()).
                build();

        LeastSquaresOptimizer optimizer = new LevenbergMarquardtOptimizer();

        LeastSquaresOptimizer.Optimum optimum = optimizer.optimize(problem);

        double[] result = optimum.getPoint().toArray();

        return from(targetRate, result);
    }


    public Rate convert(Rate originRate, Rate.Type targetRate, double... initialGuess) {
        if (originRate.getType() == targetRate) {
            throw new IllegalArgumentException("same origin and target ");
        }
        ParametricUnivariateFunction transformFunction = null;

        switch (targetRate) {
            case BF:
                transformFunction = new BFTransformFunction();
                break;

            case KISS:
                transformFunction = new KissTransformFunction();
                break;
            case RF:
                transformFunction = new RaceFlightTransformFunction();
                break;
        }

        return fit(transformFunction, originRate, targetRate, initialGuess);
    }

}
