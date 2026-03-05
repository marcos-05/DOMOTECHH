package com.gui.login;

import com.gui.login.model.DatabaseConnection;
import com.gui.login.model.Mensaje;
import com.gui.login.model.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class ChatController implements Initializable {

    @FXML private Button btn;
    @FXML private Button backBtn;
    @FXML private TextField txt;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox principal;
    @FXML private VBox vboxChat;
    @FXML private AnchorPane root;

    private final Usuario user = LoginController.usuarioActivo;
    private final DatabaseConnection db = new DatabaseConnection();
    private Vector<String> mensajesActuales = new Vector<>();
    private final int idActivo = LoginController.idActivo;

    private Thread hiloActualizador; // 🔁 Referencia al hilo

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        principal.toFront();
        String css = getClass().getResource("/css/style.css").toExternalForm();
        root.getStylesheets().add(css);

        scrollPane.setContent(vboxChat);
        scrollPane.setFitToWidth(true);
        backBtn.toFront();

        mostrarRegistro(); // Mostrar historial inicial
        iniciarActualizacionAutomatica(); // Activar refresco
    }

    @FXML
    void enviarMensaje(ActionEvent e) {
        String mensajeTexto = txt.getText().trim();
        if (!mensajeTexto.isEmpty()) {
            Mensaje mensaje = new Mensaje(mensajeTexto, LoginController.idActivo);
            if (mensaje.getIdUsuario() == LoginController.idActivo) {
                boolean exito = db.insertarMensaje(mensaje);
                if (exito) {
                    txt.clear();
                } else {
                    System.out.println("❌ Error al insertar el mensaje.");
                }
            }
        }
    }

    public void mostrarRegistro() {
        Vector<String> mensajes = db.obtenerMensajesPorComunidad(user.getNombreComunidad());
        mensajesActuales = mensajes;
        vboxChat.getChildren().clear();

        for (String mensaje : mensajes) {
            Label label = new Label(mensaje);
            label.setWrapText(true);
            label.setMaxWidth(400);
            label.setStyle("-fx-padding: 5 10 5 10;");

            if (mensaje.startsWith(user.getNombre() + ":")) {
                label.getStyleClass().add("mensaje-propio");
                label.setStyle(label.getStyle() + "-fx-alignment: CENTER_RIGHT;");
                vboxChat.setMargin(label, new javafx.geometry.Insets(5, 0, 5, 150));
            } else {
                label.getStyleClass().add("mensaje-ajeno");
                vboxChat.setMargin(label, new javafx.geometry.Insets(5, 200, 5, 10));
            }

            vboxChat.getChildren().add(label);
        }

        scrollPane.layout();
        scrollPane.setVvalue(1.0);
    }

    private void iniciarActualizacionAutomatica() {
        hiloActualizador = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Vector<String> nuevos = db.obtenerMensajesPorComunidad(user.getNombreComunidad());
                    if (!nuevos.equals(mensajesActuales)) {
                        mensajesActuales = nuevos;
                        Platform.runLater(this::mostrarRegistro);
                    }

                    Thread.sleep(2000); // 2 segundos
                } catch (InterruptedException e) {
                    System.out.println("🛑 Hilo de actualización interrumpido.");
                    break;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        hiloActualizador.setDaemon(true);
        hiloActualizador.start();
    }

    @FXML
    private void volverMenu(ActionEvent event) throws IOException {
        // 🛑 Detener hilo antes de salir
        if (hiloActualizador != null && hiloActualizador.isAlive()) {
            hiloActualizador.interrupt();
        }

        String tipo = user.getTipo().toLowerCase();
        String rutaFXML = switch (tipo) {
            case "residente" -> "/com/gui/login/menu_residente.fxml";
            case "portero"   -> "/com/gui/login/menu_portero.fxml";
            case "tecnico"   -> "/com/gui/login/menu_tecnico.fxml";
            case "presidente" -> "/com/gui/login/menu_presidente.fxml";
            default -> null;
        };

        if (rutaFXML != null) {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
