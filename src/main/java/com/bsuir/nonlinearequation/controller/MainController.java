package com.bsuir.nonlinearequation.controller;

import com.bsuir.funapproximation.logic.FunctionService;
import com.bsuir.funapproximation.logic.impl.FunctionServiceImpl;
import com.bsuir.linearsystem.model.Vector;
import com.bsuir.nonlinearequation.data.FunctionCoordinates;
import com.bsuir.nonlinearequation.utils.ViewUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static com.bsuir.funapproximation.util.VariantUtils.getF;

public class MainController {
    private final FunctionService functionService = new FunctionServiceImpl();

    @FXML
    private TextField aField;
    @FXML
    private TextField bField;

    @FXML
    private Pane pane;

    @FXML
    public void calculateAndShowFunction() throws IOException {
        double a = Double.parseDouble(aField.getText());
        double b = Double.parseDouble(bField.getText());

        Vector xVector = functionService.calculateXVector(a, b, 21);
        Vector yVector = functionService.calculateYVector(getF(), xVector);

        FunctionCoordinates functionCalculationResult = FunctionCoordinates.builder()
                .xVector(xVector)
                .yVector(yVector)
                .build();

        ViewUtils.showWindow("/resolving.fxml",
                (controller) -> ((ResolvingController) controller).initFunctionCoordinates(functionCalculationResult));
        ViewUtils.hideWindowByNode(pane);
    }
}
