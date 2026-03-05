package com.gui.login;

import com.gui.login.model.*;
import javafx.event.ActionEvent; // Importa ActionEvent para manejar eventos de acción
import javafx.fxml.FXML; // Importa FXML para la inyección de dependencias
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Importa Label para mostrar mensajes
import javafx.scene.control.TextField; // Importa TextField para entrada de texto
import javafx.fxml.FXMLLoader; // Importa FXMLLoader para cargar otras vistas
import javafx.scene.Parent; // Importa Parent para la estructura de la vista
import javafx.scene.Scene; // Importa Scene para la escena
import javafx.scene.paint.Color;
import javafx.stage.Stage; // Importa Stage para la ventana
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.*;
import org.apache.commons.codec.digest.DigestUtils;

public class LoginController {

    public static Usuario usuarioActivo;
    @FXML
    public Button btn_singup;

    @FXML
    public Button btn_login;

    @FXML
    private Label lbl_validation; // Etiqueta para mostrar mensajes de validación

    @FXML
    private TextField txt_password; // Campo de entrada de nombre de usuario

    @FXML
    private TextField txt_username;// Campo de entrada de nombre de usuario

    public static int idActivo;



    @FXML
    void loginValidate(ActionEvent event) {
        String username = txt_username.getText(); // Obtiene el nombre de usuario
        String password = txt_password.getText(); // Obtiene la contraseña

        // Verifica si los campos de texto están vacíos
        if (username.isEmpty() || password.isEmpty()) {
            lbl_validation.setText("Por favor, escribe algo"); // Mensaje de error
            lbl_validation.setTextFill(Color.RED); // Cambia el color del texto a rojo
        } else {

            // Verificamos si el usuario es igual a "presidente"
            if (verificarUsuario().equals("presidente")) {
                // Cargar la nueva ventana si el usuario es "director"
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_presidente.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMinWidth(960); // Ancho mínimo de la ventana
                stage.setMinHeight(540); // Alto mínimo de la ventana
                stage.show();
            }
            // Verificamos si el usuario es igual a "portero"
            else if (verificarUsuario().equals("portero")) {
                // Cargar la nueva ventana si el usuario es "portero"
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_portero.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMinWidth(960); // Ancho mínimo de la ventana
                stage.setMinHeight(540); // Alto mínimo de la ventana
                stage.show();
            }
            else if (verificarUsuario().equals("tecnico")) {
                // Cargar la nueva ventana si el usuario es "portero"
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_tecnico.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMinWidth(960); // Ancho mínimo de la ventana
                stage.setMinHeight(540); // Alto mínimo de la ventana
                stage.show();
            }
            else if (verificarUsuario().equals("residente")) {
                // Cargar la nueva ventana si el usuario es "portero"
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/com/gui/login/menu_residente.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMinWidth(960); // Ancho mínimo de la ventana
                stage.setMinHeight(540); // Alto mínimo de la ventana
                stage.show();
            }
            // Si el usuario no es "director" ni "portero"

            // Aquí puedes validar el usuario y contraseña
            // Para este ejemplo, asumiremos que el usuario es "admin" y la contraseña es "admin"

            else {
                // Si la contraseña es incorrecta, carga la nueva ventana
                try {
                    lbl_validation.setText("Usuario o Contraseña incorrecta");
                    txt_username.setText("");
                    txt_password.setText("");

                } catch (Exception e) {
                    e.printStackTrace(); // Imprime el error en la consola
                }
            }
        }
    }

    @FXML
    void singup(ActionEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/gui/login/singup.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(960); // Ancho mínimo de la ventana
        stage.setMinHeight(540); // Alto mínimo de la ventana
        stage.show();
    }

    public String verificarUsuario() {
        String tipoUsuario = "x";
        String username = txt_username.getText();
        String password = txt_password.getText();

        String sql = "SELECT * FROM usuario WHERE nombre = ? AND contraseña = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            String hashedPassword = DigestUtils.sha256Hex(password);
            pstmt.setString(2, hashedPassword);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_Usuario");
                int edad = rs.getInt("edad");
                String rol = rs.getString("rol");
                String correo = rs.getString("correo");
                String comunidad = rs.getString("comunidad");
                usuarioActivo = new Usuario(username, edad, correo, password, rol, comunidad);
                idActivo = id;
                tipoUsuario = rol.toLowerCase();
            }

        } catch (SQLException e) {
            System.out.println("❌ Error en la base de datos: " + e.getMessage());
        }

        return tipoUsuario;
    }
}
