package com.gui.login.model;

import java.util.ArrayList;

public class Presidente extends Usuario {

    private ArrayList<Puerta> puertasPrivadas;

    public Presidente(String nombre, int edad, String correo, String password,String nombreComunidad) {
        super(nombre, edad, correo, password,"presidente",nombreComunidad);
    }
    public void agregarPuertaPrivada(Puerta puerta) {
        puertasPrivadas.add(puerta);
    }


    public void interactuarConPuerta(Puerta puerta, boolean abrir) {
        if (puerta.getTipo().equals("Publica") || puertasPrivadas.contains(puerta)) {
            if (abrir) {
                puerta.abrir(nombre);
            } else {
                puerta.cerrar(nombre);
            }
        } else {
            System.out.println(nombre + " no tiene acceso a esta puerta.");
        }
    }
}
