package com.gui.login;
import org.apache.commons.codec.digest.DigestUtils;

import com.gui.login.model.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Vector;

public class SingupController {

    @FXML
    private Button btn_register;

    @FXML
    private Label lbl_validation;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_edad;

    @FXML
    private TextField txt_gmail;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_type;


    @FXML
    private TextField txt_nombreComunidad;





    @FXML
    void register(ActionEvent event) {

        String username = txt_username.getText();
        String rawPassword = txt_password.getText();
        String password = DigestUtils.sha256Hex(rawPassword); // 🔐 Hasheamos la contraseña
        String correo = txt_gmail.getText();
        String tipo = txt_type.getText();
        String Stringedad = txt_edad.getText();
        String nombreComunidad = txt_nombreComunidad.getText();


        if (username.isEmpty() || password.isEmpty() || correo.isEmpty() || tipo.isEmpty() || Stringedad.isEmpty() || nombreComunidad.isEmpty()) {
            lbl_validation.setText("Por favor, completa la información");
            lbl_validation.setTextFill(Color.RED);
            return;
        }

        DatabaseConnection db = new DatabaseConnection();

        try {

            int edad = Integer.parseInt(Stringedad);
            Usuario nuevoUsuario = null;

            // Aquí podrías comprobar si el usuario ya existe en base de datos si quieres.
            // Si lo haces, añade un método tipo "existeUsuario(nombre)" en DatabaseConnection

            switch (tipo.toLowerCase()) {
                case "presidente":

                    if (db.existeRolEnComunidad("presidente", nombreComunidad)) {
                        lbl_validation.setText("Ya existe un presidente en la comunidad " + nombreComunidad);
                        lbl_validation.setTextFill(Color.RED);
                        return;
                    } else {
                        nuevoUsuario = new Presidente(username, edad, correo, password, nombreComunidad);
                        break;
                    }

                case "tecnico":

                    if (db.existeRolEnComunidad("tecnico", nombreComunidad)) {
                        lbl_validation.setText("Ya existe un técnico en la comunidad " + nombreComunidad);
                        lbl_validation.setTextFill(Color.RED);
                        return;
                    } else {
                        nuevoUsuario = new Tecnico(username, edad, correo, password, nombreComunidad);
                        break;
                    }

                case "portero":

                    if (db.existeRolEnComunidad("portero", nombreComunidad)) {
                        lbl_validation.setText("Ya existe un portero en la comunidad " + nombreComunidad);
                        lbl_validation.setTextFill(Color.RED);
                        return;
                    } else {
                        nuevoUsuario = new Portero(username, edad, correo, password, nombreComunidad);
                        break;
                    }

                case "residente":
                    nuevoUsuario = new Residente(username, edad, correo, password, nombreComunidad);
                    break;

                default:
                    lbl_validation.setText("Tipo de usuario no válido");
                    lbl_validation.setTextFill(Color.RED);
                    return;
            }

            if (nuevoUsuario != null) {

                if (db.existeUsuario(username)) {
                    lbl_validation.setText("Nombre de usuario ya usado");
                    lbl_validation.setTextFill(Color.RED);
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(e -> {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/login.fxml"));
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    pause.play();
                } else {

                    boolean exito = db.insertarUsuario(nuevoUsuario);
                    if (exito) {
                        lbl_validation.setText("Usuario registrado, ¡puede iniciar sesión!");
                        lbl_validation.setTextFill(Color.LIGHTGREY);

                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(e -> {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/com/gui/login/login.fxml"));
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        pause.play();
                    } else {
                        lbl_validation.setText("Error al insertar en la base de datos");
                        lbl_validation.setTextFill(Color.RED);
                    }

                }
            }


        } catch (NumberFormatException e) {

            lbl_validation.setText("La edad debe ser un número");
            lbl_validation.setTextFill(Color.RED);
    }
}



}
