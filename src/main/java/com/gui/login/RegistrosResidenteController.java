package com.gui.login;

import com.gui.login.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.gui.login.model.DatabaseConnection;


public class RegistrosResidenteController implements Initializable {

    @FXML
    private Label lbl;

    public ArrayList<String> registros = new ArrayList<>();
    private Usuario user = LoginController.usuarioActivo;
    private int idActivo = LoginController.idActivo;
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StringBuilder mensajesEncontrados = new StringBuilder();

        if (user.getTipo().equals("residente")) {
            // Recuperar SOLO los registros del usuario
            Vector<String> registros = db.obtenerRegistrosPorUsuario(idActivo, user.getNombreComunidad());
            for (String mensaje : registros) {
                mensajesEncontrados.append(mensaje).append("\n");
            }
        } else {
            // Recuperar TODOS los registros
            Vector<String> registros = db.obtenerTodosLosRegistros(user.getNombreComunidad());
            for (String mensaje : registros) {
                mensajesEncontrados.append(mensaje).append("\n");
            }
        }

        lbl.setText(mensajesEncontrados.toString());
    }

    @FXML
    public void mostrarRegistro(ActionEvent e) {

    }

    @FXML
    private void volverMenu(ActionEvent event) throws IOException {
        // Cargar la ventana de inicio presidente.fxml
        if (user.getTipo().equals("residente")) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_residente.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (user.getTipo().equals("portero")) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_portero.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (user.getTipo().equals("tecnico")) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_tecnico.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (user.getTipo().equals("presidente")) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_presidente.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}




