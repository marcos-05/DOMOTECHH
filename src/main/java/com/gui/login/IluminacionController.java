package com.gui.login;

import com.gui.login.model.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class IluminacionController {

    @FXML
    private Button btn_volvermenu; // Botón para abrir la puerta
    @FXML
    private Slider slider1;
    @FXML
    private Slider slider2;
    @FXML
    private Slider slider3;
    @FXML
    private Slider slider4;
    @FXML
    private ColorPicker colorpicker1;
    @FXML
    private ColorPicker colorpicker2;
    @FXML
    private ColorPicker colorpicker3;
    @FXML
    private ColorPicker colorpicker4;
    @FXML
    private Button mapa;

    private DatabaseConnection d = new DatabaseConnection();


    // Método para inicializar los controles según los valores de los sensores
    private void inicializarControlesSegunSensores() {

        Float temperatura = d.obtenerUltimoDatoSensor(5);
        Float intensidadLuz = d.obtenerUltimoDatoSensor(6);

        float temperatura_primitivo = 500;
        if (temperatura != null) {
            temperatura_primitivo = temperatura.floatValue();
        } else {
            System.out.println("No hay dato disponible.");
        }
        float intensidadLuz_primitivo = 500;
        if (intensidadLuz != null) {
            intensidadLuz_primitivo = intensidadLuz.floatValue();
        } else {
            System.out.println("No hay dato disponible.");
        }


        // Ajustar los sliders y colorpickers en función de la temperatura
        if (temperatura_primitivo < 15) { // Frío

            colorpicker1.setValue(Color.YELLOW);
            colorpicker2.setValue(Color.YELLOW);
            colorpicker3.setValue(Color.YELLOW);
            colorpicker4.setValue(Color.YELLOW);


        } else if (temperatura_primitivo > 30) { // Calor

            colorpicker1.setValue(Color.CYAN);
            colorpicker2.setValue(Color.CYAN);
            colorpicker3.setValue(Color.CYAN);
            colorpicker4.setValue(Color.CYAN);


        } else { // Temperatura normal
            // Para temperatura moderada, establecer un color neutral (blanco)
            colorpicker1.setValue(Color.WHITE);
            colorpicker2.setValue(Color.WHITE);
            colorpicker3.setValue(Color.WHITE);
            colorpicker4.setValue(Color.WHITE);

        }

        // Ajustar los controles en función de la intensidad de la luz
        if (intensidadLuz_primitivo < 30) { // Poca luz
            // Si hay poca luz, aumentar la intensidad de los sliders
            slider1.setValue(80);
            slider2.setValue(80);
            slider3.setValue(80);
            slider4.setValue(80);
        } else if (intensidadLuz_primitivo  > 70) { // Mucha luz
            // Si hay mucha luz, reducir la intensidad de los sliders
            slider1.setValue(30);
            slider2.setValue(30);
            slider3.setValue(30);
            slider4.setValue(30);
        } else { // Luz normal
            // Luz normal
            slider1.setValue(50);
            slider2.setValue(50);
            slider3.setValue(50);
            slider4.setValue(50);
        }
    }

    // Método que se ejecuta cuando la ventana se inicializa
    @FXML
    public void initialize() {
        // Llamar a la función para inicializar los controles con base en los sensores
        inicializarControlesSegunSensores();
    }

    @FXML
    private void VolverInicio(ActionEvent event) throws IOException {
        // Cargar la ventana de inicio presidente.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_portero.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void Mapa(ActionEvent event) throws IOException {
        // Cargar la ventana de inicio presidente.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/Mapa.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
