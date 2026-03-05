package com.gui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Vector;

public class SaldoControlador {

    @FXML
    private Label lbl_validation;
    @FXML
    private Label lbl_validation1;
    @FXML
    private Label lbl_validation2;



    public void initialize() {

    }

    private void actualizarSaldo() {

    }


    @FXML
    private void VolverInicio(ActionEvent event) throws IOException {
        // Cargar la ventana de inicio presidente.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_presidente.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

