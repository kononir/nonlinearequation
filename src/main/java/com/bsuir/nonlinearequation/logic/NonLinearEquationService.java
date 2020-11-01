package com.bsuir.nonlinearequation.logic;

import com.bsuir.linearsystem.model.Vector;

import java.util.function.Function;

public interface NonLinearEquationService {
    Vector solveEquation(double alpha, double eps, Function<Double, Double> fun);
}
