package com.bsuir.nonlinearequation.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FunctionRootTableRow {
    private final IntegerProperty number;
    private final DoubleProperty root;
    private final IntegerProperty iteration;

    public FunctionRootTableRow(int number, double root, int iteration) {
        this.number = new SimpleIntegerProperty(number);
        this.root = new SimpleDoubleProperty(root);
        this.iteration = new SimpleIntegerProperty(iteration);
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public double getRoot() {
        return root.get();
    }

    public DoubleProperty rootProperty() {
        return root;
    }

    public int getIteration() {
        return iteration.get();
    }

    public IntegerProperty iterationProperty() {
        return iteration;
    }
}
