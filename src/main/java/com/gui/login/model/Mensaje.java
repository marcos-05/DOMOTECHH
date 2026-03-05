package com.gui.login.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Mensaje {
    private String mensaje;
    private Timestamp fecha;
    private int idUsuario;


    public Mensaje(String mensaje, int idUsuario) {
        this.mensaje = mensaje;
        this.fecha = Timestamp.valueOf(LocalDateTime.now());
        this.idUsuario = idUsuario;
    }

    public String getMensaje() {
        return this.mensaje;
    }
    public Timestamp getFecha() {
        return this.fecha;
    }
    public int getIdUsuario() {
        return this.idUsuario;
    }

}
