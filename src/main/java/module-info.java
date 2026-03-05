module com.gui.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.desktop;
    requires java.sql;
    requires org.apache.commons.codec;

    opens com.gui.login.model to com.google.gson;
    opens com.gui.login to javafx.fxml, com.google.gson; // Permitir acceso al paquete
    exports com.gui.login; // Exporta el paquete para que sea accesible
}
