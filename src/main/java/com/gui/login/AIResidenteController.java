package com.gui.login;

import com.gui.login.model.*;
import javafx.event.ActionEvent; // Importa ActionEvent para manejar eventos de acción
import javafx.fxml.FXML; // Importa FXML para la inyección de dependencias
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Importa Label para mostrar mensajes
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Vector;

public class AIResidenteController {

    @FXML
    private Button btnPP; // Botón para abrir la puerta
    @FXML
    private Button btnGaraje;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnGaraje2;
    @FXML
    private Label lbl;
    private int idActivo = LoginController.idActivo;

    private Usuario user = LoginController.usuarioActivo; // Obtiene el usuario activo
    private ListaEnlazada<String> registross = new ListaEnlazada<>(); // Lista de registros

    // Método para agregar un registro al sistema (puerta abierta)
    /*private void agregarRegistro(String descripcionPuerta) {
        Vector<String> registrosExistentes = AdminBaseDatos.deserializarJsonAArray();

        // Crear un nuevo objeto de Registro con la descripción de la puerta
        Registro res = new Registro(user.getNombre(), descripcionPuerta, obtenerIdSensor(descripcionPuerta));

        // Evitar duplicados
        if (!registrosExistentes.contains(res.toString())) {
            registrosExistentes.add(res.toString());

            // Convertir la lista de registros en una Lista Enlazada para mantener el orden
            ListaEnlazada<String> listaEnlazada = new ListaEnlazada<>();
            for (String registro : registrosExistentes) {
                listaEnlazada.add(registro);
            }

            // Serializar la lista y guardarla en la base de datos
            AdminBaseDatos.serializarRegistros(listaEnlazada);
            lbl.setText("Puerta " + descripcionPuerta + " abierta");
        }
    }

    // Método para agregar un registro de sensores de movimiento
    private void agregarRegistroSensores(String descripcionPuerta) {
        Vector<String> registrosSensoresExistentes = AdminBaseDatos.deserializarJsonAArrayRegistrosMov();
        RegistroSensorMov resMov = new RegistroSensorMov(user.getNombre(), descripcionPuerta);

        // Evitar duplicados
        if (!registrosSensoresExistentes.contains(resMov.toString())) {
            registrosSensoresExistentes.add(resMov.toString());

            // Invertir el orden de los registros
            ListaEnlazada<String> listaEnlazada = new ListaEnlazada<>();
            Collections.reverse(registrosSensoresExistentes);
            for (String registro : registrosSensoresExistentes) {
                listaEnlazada.add(registro);
            }

            // Guardar los registros de sensores
            AdminBaseDatos.serializarRegistrosDeMov(listaEnlazada);
        }
    }

    // Método para agregar un registro de sensores para apagar la puerta
    private void agregarRegistroSensoresApagar(String tipoDePuerta) {
        Vector<String> registrosSensoresExistentess = AdminBaseDatos.deserializarJsonAArrayRegistrosMov();
        RegistroSensorMov resMov = new RegistroSensorMov(user.getNombre(), tipoDePuerta);

        // Evitar duplicados
        if (!registrosSensoresExistentess.contains(resMov.toString2())) {
            registrosSensoresExistentess.add(resMov.toString2());

            // Invertir el orden de los registros
            ListaEnlazada<String> listaEnlazada = new ListaEnlazada<>();
            Collections.reverse(registrosSensoresExistentess);
            for (String registro : registrosSensoresExistentess) {
                listaEnlazada.add(registro);
            }

            // Guardar los registros de sensores
            AdminBaseDatos.serializarRegistrosDeMov(listaEnlazada);
        }
    }*/

    // Método para abrir la puerta principal
    @FXML
    public void abrirPuertaPrincipal(ActionEvent e) {
        if (user == null) {
            System.err.println("❌ Error: Usuario no autenticado.");
            return;
        }

        // Crear el registro de la puerta principal
        Registro registro = new Registro(user.getNombre(), "Puerta Principal", obtenerIdSensor("Puerta Principal"), idActivo);
        DatabaseConnection db = new DatabaseConnection();
        db.insertarSensor(registro); // Guardar en la base de datos
        lbl.setText("Puerta " + registro.getDescripcion() + " abierta");


    }

    // Método para abrir la puerta del garaje 1
    @FXML
    public void abrirPuertaGaraje1(ActionEvent e) {
        if (user == null) {
            System.err.println("❌ Error: Usuario no autenticado.");
            return;
        }

        // Crear el registro del garaje 1
        Registro registro = new Registro(user.getNombre(), "Garaje 1", obtenerIdSensor("Garaje 1"), idActivo);
        DatabaseConnection db = new DatabaseConnection();
        db.insertarSensor(registro);
        lbl.setText("Puerta " + registro.getDescripcion() + " abierta");
    }
    public void abrirPuertaGaraje2(ActionEvent e) {
        if (user == null) {
            System.err.println("❌ Error: Usuario no autenticado.");
            return;
        }

        // Crear el registro del garaje 1
        Registro registro = new Registro(user.getNombre(), "Garaje 2", obtenerIdSensor("Garaje 2"), idActivo);
        DatabaseConnection db = new DatabaseConnection();
        db.insertarSensor(registro);
        lbl.setText("Puerta " + registro.getDescripcion() + " abierta");
    }

    // Método para abrir la puerta del gimnasio
    @FXML
    public void abrirPuertaGimnasio(ActionEvent e) {
        if (user == null) {
            System.err.println("❌ Error: Usuario no autenticado.");
            return;
        }

        // Crear el registro del gimnasio
        Registro registro = new Registro(user.getNombre(), "Gimnasio", obtenerIdSensor("Gimnasio"), idActivo);
        DatabaseConnection db = new DatabaseConnection();
        db.insertarSensor(registro);
        lbl.setText("Puerta " + registro.getDescripcion() + " abierta");

    }

    // Método auxiliar para obtener el ID del sensor
    private int obtenerIdSensor(String ubicacion) {
        DatabaseConnection db = new DatabaseConnection();
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT id_Sensor FROM asignacion_sensor WHERE ubicacion = ?")) {

            pstmt.setString(1, ubicacion);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_Sensor");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener el ID del sensor: " + e.getMessage());
        }
        return -1; // Retorna -1 si no se encontró
    }

    // Método para volver al menú correspondiente según el tipo de usuario
    @FXML
    private void volverMenu(ActionEvent event) throws IOException {
        String tipoUsuario = user.getTipo();

        String rutaMenu = "";
        switch (tipoUsuario) {
            case "residente":
                rutaMenu = "/com/gui/login/menu_residente.fxml";
                break;
            case "portero":
                rutaMenu = "/com/gui/login/menu_portero.fxml";
                break;
            case "tecnico":
                rutaMenu = "/com/gui/login/menu_tecnico.fxml";
                break;
            case "presidente":
                rutaMenu = "/com/gui/login/menu_presidente.fxml";
                break;
        }

        if (!rutaMenu.isEmpty()) {
            Parent root = FXMLLoader.load(getClass().getResource(rutaMenu));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    // Método para mostrar el registro (ejemplo de cómo agregar registros)
    public void mostrarRegistro(ArrayList<String> array, String elemento) {
        array.add(elemento);
    }}

