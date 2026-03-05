package com.gui.login.model;

public class Tecnico extends Usuario {

    public Tecnico(String nombre, int edad, String correo, String password, String nombreComunidad) {
        super(nombre, edad, correo, password, "tecnico",nombreComunidad);  // Aquí pasamos "Tecnico" al constructor de Usuario
    }
}
