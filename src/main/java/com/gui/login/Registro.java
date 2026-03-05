package com.gui.login;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Registro {
    private String nombreUsuario;  // Usamos el nombre en lugar del ID
    private String descripcion;
    private int idSensor;
    private int idUsuario;
    private Timestamp fecha;

    public Registro(String nombreUsuario, String descripcion, int idSensor, int idUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.descripcion = descripcion;
        this.fecha = Timestamp.valueOf(LocalDateTime.now());
        this.idSensor = idSensor;
        this.idUsuario = idUsuario;
    }
    public Timestamp getFecha() {
        return fecha;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdSensor() {
        return idSensor;
    }
}