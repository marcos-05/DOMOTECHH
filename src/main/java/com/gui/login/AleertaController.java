package com.gui.login;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AleertaController {

    @FXML
    private Button volver;

    @FXML
    private void Volvertecnico(ActionEvent event) throws IOException {
        // Cargar la ventana de inicio presidente.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_tecnico.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
