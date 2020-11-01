package com.bsuir.nonlinearequation.utils;

import com.bsuir.linearsystem.model.Vector;
import com.bsuir.nonlinearequation.data.FunctionCoordinates;
import com.bsuir.nonlinearequation.view.FunctionCoordinateTableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TaskUtils {
    public static final String EPS = "0.0001";

    public static ObservableList<FunctionCoordinateTableRow> convertToObservableList(FunctionCoordinates results) {
        Vector xVector = results.getXVector();
        Vector yVector = results.getYVector();

        int m = xVector.len();

        List<FunctionCoordinateTableRow> resultsList = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            resultsList.add(new FunctionCoordinateTableRow(xVector.get(i), yVector.get(i)));
        }

        return FXCollections.observableArrayList(resultsList);
    }
}
