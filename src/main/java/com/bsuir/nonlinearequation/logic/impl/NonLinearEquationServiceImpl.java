package com.bsuir.nonlinearequation.logic.impl;

import com.bsuir.linearsystem.model.Vector;
import com.bsuir.nonlinearequation.logic.NonLinearEquationService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NonLinearEquationServiceImpl implements NonLinearEquationService {
    @Override
    public Vector solveEquation(double alpha, double eps, Function<Double, Double> fun) {
        List<Double> xkList = new ArrayList<>();
        double x0 = alpha;
        xkList.add(x0);
        double x1;
        double de;
        int it = 0;
        do {
            it++;
            x1 = fun.apply(x0);
            de = Math.abs(x1 - x0);
            x0 = x1;
            xkList.add(x0);
        } while (de >= eps && it <= 100);
        return new Vector(xkList);
    }
}
