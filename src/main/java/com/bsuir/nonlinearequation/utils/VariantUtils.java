package com.bsuir.nonlinearequation.utils;

import java.util.function.Function;

import static com.bsuir.diffintegralcalc.utils.VariantUtils.getFirstDifferentialOfF;
import static com.bsuir.funapproximation.util.VariantUtils.getF;

public class VariantUtils {
    public static Function<Double, Double> getPhi() {
        return (x) -> x - getF().apply(x) / getFirstDifferentialOfF().apply(x);
    }
}
