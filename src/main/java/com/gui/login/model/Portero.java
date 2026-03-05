package com.gui.login.model;

public class Portero extends Usuario {

    public Portero(String nombre, int edad, String correo, String password, String nombreComunidad) {
        super(nombre, edad, correo, password,"portero",nombreComunidad);
    }
    public void interactuarConPuerta(Puerta puerta, boolean abrir) {
        if (puerta.getTipo().equals("Publica")) {
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


