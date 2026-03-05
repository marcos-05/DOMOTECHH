package com.gui.login;

import com.gui.login.model.DatabaseConnection;
import com.gui.login.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;



public class TecnicoController {

    @FXML
    private Label user;
    @FXML
    private VBox vboxPrincipal;
    @FXML
    public Button logout;
    @FXML
    private VBox vboxPrincipal2;

    private Usuario usuario = LoginController.usuarioActivo;
    @FXML
    public Button gastos;
    @FXML
    public Button controltecnico;
    @FXML
    public Button chat;
    @FXML
    public Button btnPulsometro;

    @FXML
    private Label etiquetaAvisoPulsometro;



    @FXML
    private void initialize() {


        vboxPrincipal2.toFront();

        // Establecer el nombre del usuario activo en el Label
        if (usuario != null) {
            user.setText("User: " + usuario.getNombre()); // Asumimos que 'usuario' tiene un método 'getNombre()'
        } else {
            user.setText("User: Desconocido"); // Caso por si el usuario es null
        }
    }
    @FXML
    private void setControltecnico(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/login/menu_alertas.fxml"));
            Parent nuevaVentanaRoot = loader.load();

            // Crear una nueva escena con el contenido cargado
            Scene nuevaVentanaScene = new Scene(nuevaVentanaRoot);

            // Obtener la ventana actual (stage) a partir del evento
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Establecer la nueva escena en la ventana actual
            currentStage.setScene(nuevaVentanaScene);

            // Mostrar la ventana con la nueva escena
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Para propósitos de depuración
        }}

    @FXML
    private void chat(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/login/chat.fxml"));
            Parent nuevaVentanaRoot = loader.load();

            // Crear una nueva escena con el contenido cargado
            Scene nuevaVentanaScene = new Scene(nuevaVentanaRoot);

            // Obtener la ventana actual (stage) a partir del evento
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Establecer la nueva escena en la ventana actual
            currentStage.setScene(nuevaVentanaScene);

            // Mostrar la ventana con la nueva escena
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Para propósitos de depuración
        }}


    @FXML
    private void handleSaldoButtonAction2(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/login/saldo_view.fxml"));
            Parent nuevaVentanaRoot = loader.load();

            // Crear una nueva escena con el contenido cargado
            Scene nuevaVentanaScene = new Scene(nuevaVentanaRoot);

            // Obtener la ventana actual (stage) a partir del evento
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Establecer la nueva escena en la ventana actual
            currentStage.setScene(nuevaVentanaScene);

            // Mostrar la ventana con la nueva escena
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Para propósitos de depuración
        }
    }

    @FXML
    private void VolverLogin(ActionEvent event) throws IOException {
        // Cargar la ventana de inicio presidente.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void onGenerarDatoPulsometro(ActionEvent event) {
        DatabaseConnection db = new DatabaseConnection();
        int idSensorPulsometro = 8; // El ID del sensor pulsómetro

        boolean resultado = db.generarEInsertarDatoPulsometro(8);
        if (resultado) {
            etiquetaAvisoPulsometro.setText("✅ Pulso y tensión registradas");
            etiquetaAvisoPulsometro.setStyle("-fx-text-fill: green;");
        } else {
            etiquetaAvisoPulsometro.setText("❌ Error al registrar el dato");
            etiquetaAvisoPulsometro.setStyle("-fx-text-fill: red;");
        }
    }

}
