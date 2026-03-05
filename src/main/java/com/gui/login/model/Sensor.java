package com.gui.login.model;

import java.util.Random;

public class Sensor {

    private int idSensor; // Tipo de sensor (e.g., "Temperatura", "Luz")
    private String ubicacion; // Valor mínimo que puede medir el sensor
    private int idUsuarioAsginado; // Valor máximo que puede medir el sensor
    private double valorActual; // Valor actual del sensor

    private int id; //Id del sensor

    // Constructor
    public Sensor(String ubicacion, int idUsuarioAsginado) {
        this.ubicacion=ubicacion;
        this.idUsuarioAsginado=idUsuarioAsginado;
    }

    // Método para simular la lectura del sensor




    // Getters y Setters



    public int getId(){
        return idSensor;
    }
    public String getUbicacion(){
        return ubicacion;
    }

    public int getIdUsuarioAsginado(){
        return idUsuarioAsginado;
    }



}