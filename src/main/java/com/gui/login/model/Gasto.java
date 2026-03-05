package com.gui.login.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Gasto {
    private int id;
    private int idUsuario;
    private String tipoGasto;
    private double cantidad;
    private Date fecha;


    public Gasto(int idUsuario, String tipoGasto, double cantidad, Date fecha) {
        this.idUsuario = idUsuario;
        this.tipoGasto = tipoGasto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    // Getters y setters...

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getTipoGasto() { return tipoGasto; }
    public void setTipoGasto(String tipoGasto) { this.tipoGasto = tipoGasto; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}


