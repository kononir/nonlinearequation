package com.bsuir.nonlinearequation.controller;

import com.bsuir.funapproximation.util.ViewUtils;
import com.bsuir.linearsystem.model.Vector;
import com.bsuir.nonlinearequation.data.FunctionCoordinates;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class ResultsController {
    @FXML
    private LineChart<Number, Number> functionGraphic;
    @FXML
    private LineChart<Number, Number> rootsGraphics;
    @FXML
    private VBox uniqueRootsHBox;

    @FXML
    private Pane pane;

    public void initFunctionCoordinates(FunctionCoordinates fCoords) {
        ViewUtils.addSeries(functionGraphic, "f(x)", fCoords.getXVector(), fCoords.getYVector());
    }

    public void initResolvingResults(List<FunctionCoordinates> results) {
        for (int i = 0; i < results.size(); i++) {
            FunctionCoordinates result = results.get(i);
            ViewUtils.addSeries(rootsGraphics, "φ" + i + "(x)", result.getXVector(), result.getYVector());
        }
        Vector yEqualsXVector = buildYEqualsXVector(results);
        ViewUtils.addSeries(rootsGraphics, "y=x", yEqualsXVector, yEqualsXVector);

        uniqueRootsHBox.getChildren().addAll(buildResolvingResultLabels(results));
    }

    @FXML
    public void goToMainPage() throws IOException {
        com.bsuir.nonlinearequation.utils.ViewUtils.showWindowWithoutInitialization("/main.fxml");
        com.bsuir.nonlinearequation.utils.ViewUtils.hideWindowByNode(pane);
    }

    private Vector buildYEqualsXVector(List<FunctionCoordinates> results) {
        int min = (int) Math.floor(findElXOrY(results, Math::min)) - 1;
        int max = (int) Math.ceil(findElXOrY(results, Math::max)) + 1;
        Vector vector = new Vector(max - min + 1);
        for (int el = min, j = 0; el <= max; el++, j++) {
            vector.set(j, el);
        }
        return vector;
    }

    private double findElXOrY(List<FunctionCoordinates> results, BiFunction<Double, Double, Double> fun) {
        double el = results.get(0).getXVector().get(0);
        for (FunctionCoordinates result : results) {
            Vector xVector = result.getXVector();
            Vector yVector = result.getYVector();
            el = fun.apply(fun.apply(findEl(xVector, fun), findEl(yVector, fun)), el);
        }
        return el;
    }

    private double findEl(Vector vector, BiFunction<Double, Double, Double> fun) {
        double el = vector.get(0);
        for (double v : vector) {
            el = fun.apply(v, el);
        }
        return el;
    }

    private List<Label> buildResolvingResultLabels(List<FunctionCoordinates> results) {
        List<Label> zValueLines = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            FunctionCoordinates result = results.get(i);
            Vector xVector = result.getXVector();
            String zValueLine = "z" + i + " = " + xVector.get(xVector.len() - 1);
            zValueLines.add(new Label(zValueLine));
        }
        return zValueLines;
    }
}
