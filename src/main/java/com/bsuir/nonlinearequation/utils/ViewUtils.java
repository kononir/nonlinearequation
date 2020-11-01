package com.bsuir.nonlinearequation.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class ViewUtils {
    private ViewUtils() {
    }

    public static void showWindow(String path, Consumer<Object> controllerInitializer) throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewUtils.class.getResource(path));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        Object controller = loader.getController();
        controllerInitializer.accept(controller);

        stage.show();
    }

    public static void showWindowWithoutInitialization(String path) throws IOException {
        showWindow(path, (controller) -> {});
    }

    public static void hideWindowByNode(Node node) {
        node.getScene().getWindow().hide();
    }
}
