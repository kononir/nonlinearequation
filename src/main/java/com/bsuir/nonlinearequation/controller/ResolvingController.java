package com.bsuir.nonlinearequation.controller;

import com.bsuir.funapproximation.logic.FunctionService;
import com.bsuir.funapproximation.logic.impl.FunctionServiceImpl;
import com.bsuir.linearsystem.model.Vector;
import com.bsuir.nonlinearequation.data.FunctionCoordinates;
import com.bsuir.nonlinearequation.logic.NonLinearEquationService;
import com.bsuir.nonlinearequation.logic.impl.NonLinearEquationServiceImpl;
import com.bsuir.nonlinearequation.utils.TaskUtils;
import com.bsuir.nonlinearequation.utils.VariantUtils;
import com.bsuir.nonlinearequation.utils.ViewUtils;
import com.bsuir.nonlinearequation.view.FunctionCoordinateTableRow;
import com.bsuir.nonlinearequation.view.FunctionRootTableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResolvingController {
    private final NonLinearEquationService nonLinearEquationService = new NonLinearEquationServiceImpl();
    private final FunctionService functionService = new FunctionServiceImpl();

    @FXML
    private TableView<FunctionCoordinateTableRow> functionTable;
    @FXML
    private TableColumn<FunctionCoordinateTableRow, Double> functionXColumn;
    @FXML
    private TableColumn<FunctionCoordinateTableRow, Double> functionYColumn;

    @FXML
    private TableView<FunctionRootTableRow> rootsTable;
    @FXML
    private TableColumn<FunctionRootTableRow, Integer> rootsNumColumn;
    @FXML
    private TableColumn<FunctionRootTableRow, Double> rootsRootColumn;
    @FXML
    private TableColumn<FunctionRootTableRow, Integer> rootsItColumn;

    @FXML
    private TextField alphaField;
    @FXML
    private TextField epsField;

    @FXML
    private Pane pane;

    private FunctionCoordinates functionCalculationResult;
    private final List<FunctionCoordinates> nonLinearSystemResolvingResults = new ArrayList<>();

    @FXML
    private void initialize() {
        functionXColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        functionYColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty().asObject());

        rootsNumColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        rootsRootColumn.setCellValueFactory(cellData -> cellData.getValue().rootProperty().asObject());
        rootsItColumn.setCellValueFactory(cellData -> cellData.getValue().iterationProperty().asObject());
    }

    public void initFunctionCoordinates(FunctionCoordinates functionCalculationResult) {
        this.functionCalculationResult = functionCalculationResult;
        functionTable.setItems(TaskUtils.convertToObservableList(functionCalculationResult));
    }

    @FXML
    public void resolveNonLinearEquation() throws IOException {
        double alpha = Double.parseDouble(alphaField.getText());
        double eps = Double.parseDouble(epsField.getText());

        Vector xkVector = nonLinearEquationService.solveEquation(alpha, eps, VariantUtils.getPhi());
        Vector ykVector = functionService.calculateYVector(VariantUtils.getPhi(), xkVector);

        int it = xkVector.len() - 1;
        double z = xkVector.get(it);

        ObservableList<FunctionRootTableRow> rootsTableItems = rootsTable.getItems();
        if (rootsTableItems != null) {
            int currNumber = rootsTableItems.size();
            FunctionRootTableRow functionRootTableRow = new FunctionRootTableRow(currNumber + 1, z, it);
            rootsTableItems.add(functionRootTableRow);
        } else {
            FunctionRootTableRow functionRootTableRow = new FunctionRootTableRow(1, z, it);
            rootsTable.setItems(FXCollections.observableArrayList(functionRootTableRow));
        }

        nonLinearSystemResolvingResults.add(FunctionCoordinates.builder()
                .xVector(xkVector)
                .yVector(ykVector)
                .build());

        showIsAllRootsAlert();
    }

    @FXML
    public void showIsAllRootsAlert() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);

        alert.setTitle("Корни");
        alert.setHeaderText("Найдены все корни?");

        ButtonType yesButton = new ButtonType("Да");
        ButtonType noButton = new ButtonType("Нет");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yesButton, noButton);

        Optional<ButtonType> answerOption = alert.showAndWait();

        if (answerOption.isPresent() && yesButton.equals(answerOption.get())) {
            showResultsWindow();
            ViewUtils.hideWindowByNode(pane);
        } else {
            alphaField.clear();
            epsField.clear();
            epsField.setText(TaskUtils.EPS);
        }
    }

    private void showResultsWindow() throws IOException {
        ViewUtils.showWindow("/results.fxml",
                (c) -> {
                    ResultsController controller = ((ResultsController) c);
                    controller.initFunctionCoordinates(functionCalculationResult);
                    controller.initResolvingResults(nonLinearSystemResolvingResults);
                });
    }
}
