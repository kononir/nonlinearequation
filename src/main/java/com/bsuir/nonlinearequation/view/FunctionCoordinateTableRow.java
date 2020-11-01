package com.bsuir.nonlinearequation.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FunctionCoordinateTableRow {
    private final DoubleProperty x;
    private final DoubleProperty y;

    public FunctionCoordinateTableRow(double x, double y) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }
}
