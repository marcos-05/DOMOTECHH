package com.gui.login.model;

import com.gui.login.LoginController;

import java.time.LocalDateTime;

public class RegistroSensorMov {
    private LocalDateTime timestamp;
    private String usuario;
    private String tipoPuerta;


    public RegistroSensorMov(String usuario, String tipoPuerta) {
        this.usuario = usuario;
        this.timestamp = LocalDateTime.now();
        this.tipoPuerta = tipoPuerta;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUsuario() {
        return usuario;
    }
   /* public String getUbicacion() {
        return ubicacion;
    }*/

    @Override
    public String toString() {
        return " Movimiento detectado en " + tipoPuerta + " por " + usuario + " ~ (" + LoginController.usuarioActivo.getTipo() + ")"+ " a las " + timestamp+" .Luz encendida\n";
    }
    public String toString2() {
        return " Movimiento detectado en " + tipoPuerta + " por " + usuario + " ~ (" + LoginController.usuarioActivo.getTipo() + ")"+ " a las " + timestamp+" .Luz apagada\n";
    }
}
